package cz.muni.fi.pa093;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
public class Delaunay {
    
    public static List<Triangle> delaunayTriangles(List<Point> points) {
        return delaunay(points, new ArrayList<>());
    }
    
    public static List<Line> delaunayLines(List<Point> points) {
        List<Line> lines = new ArrayList<>();
        delaunay(points, lines);
        return lines;
    }
    
    public static List<Triangle> delaunay(List<Point> points, List<Line> lines) {
        
        if (points.size() < 3) {
            return Collections.EMPTY_LIST;
        }
        
        Random random = new Random(System.currentTimeMillis());
        Point p1 = points.get(random.nextInt(points.size()-1));
        
        Point p2 = new Point(Float.MAX_VALUE, Float.MAX_VALUE);
        
        for (Point p : points) {
            if (!p1.equals(p) && Point.getDistance(p1, p) < Point.getDistance(p1, p2)) {
                p2 = p;
            }
        }
        
        Line e = new Line(p1, p2);
        Point p = getPointWithMinDelaunayDistanceOnLeft(e, points);
        Line e2 = new Line(p2, p);
        Line e3 = new Line(p, p1);
        
        if (p == null) {
            e = new Line(p2, p1);
            p = getPointWithMinDelaunayDistanceOnLeft(e, points);
            e2 = new Line(p, p2);
            e3 = new Line(p1, p);
        }
        
        List<Line> AEL = new ArrayList<>();
        List<Line> DT = new ArrayList<>();
        List<Triangle> triangles = new ArrayList<>();
        addToAEL(e, AEL, DT);
        addToAEL(e2, AEL, DT);
        addToAEL(e3, AEL, DT);
        triangles.add(new Triangle(p, p1, p2));

        while (!AEL.isEmpty()) {
            e = AEL.get(0);
            p1 = e.getStartPoint();
            p2 = e.getEndPoint();
            Line eSwapped = new Line(p2, p1);
            p = getPointWithMinDelaunayDistanceOnLeft(eSwapped, points);
            if (p != null) {
                e2 = new Line(p, eSwapped.getStartPoint());
                e3 = new Line(eSwapped.getEndPoint(), p);
                if (!DT.contains(e2) && !AEL.contains(e2) && 
                    !DT.contains(new Line(e2.getEndPoint(), e2.getStartPoint())) &&
                    !AEL.contains(new Line(e2.getEndPoint(), e2.getStartPoint()))) {
                    addToAEL(e2, AEL, DT);
                }                
                if (!DT.contains(e3) && !AEL.contains(e3) && 
                    !DT.contains(new Line(e3.getEndPoint(), e3.getStartPoint())) &&
                    !AEL.contains(new Line(e3.getEndPoint(), e3.getStartPoint()))) {
                    addToAEL(e3, AEL, DT);
                }
                triangles.add(new Triangle(p, p1, p2));
            }
            AEL.remove(e);
        }
        lines.clear();
        lines.addAll(DT);
        return triangles;
        
    }

    private static Point getPointWithMinDelaunayDistanceOnLeft(Line e, List<Point> points) {
        Point minOnLeft = null;
        for (Point p : points) {
            if (Point.isOnLeft(e.getStartPoint(), p, e.getEndPoint())) {
                if (minOnLeft == null) {
                    minOnLeft = p;
                    continue;
                }
                if (getDelaunayDistance(e, p) < getDelaunayDistance(e, minOnLeft)) {
                    minOnLeft = p;
                }
            }
        }
        return minOnLeft;
    }
    
    public static float getDelaunayDistance(Line e, Point p) {
        Point center = Triangle.getCenterOfCircumscribedCircle(e.getStartPoint(), e.getEndPoint(), p);
        if (Point.isOnLeft(e.getStartPoint(), center, e.getEndPoint())) {
            return Point.getDistance(p, center);
        }
        return - Point.getDistance(p, center);
    }
    
    

    private static void addToAEL(Line e, List<Line> AEL, List<Line> DT) {
        Line eSwapped = new Line(e.getEndPoint(), e.getStartPoint());
        if (AEL.contains(eSwapped)) {
            AEL.remove(e);
        } else {
            AEL.add(e);
        }
        DT.add(e);
    }


}
