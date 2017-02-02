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
    
    public static List<Line> delaunay(List<Point> points) {
        
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
        addToAEL(e, AEL, DT);
        addToAEL(e2, AEL, DT);
        addToAEL(e3, AEL, DT);

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
            }
            AEL.remove(e);
        }
        
        return DT;
        
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
        Point center = getCenterOfCircumscribedCircle(e.getStartPoint(), e.getEndPoint(), p);
        if (Point.isOnLeft(e.getStartPoint(), center, e.getEndPoint())) {
            return Point.getDistance(p, center);
        }
        return - Point.getDistance(p, center);
    }
    
    //https://en.wikipedia.org/wiki/Circumscribed_circle#Cartesian_coordinates_2
    public static Point getCenterOfCircumscribedCircle(Point a, Point b, Point c) {
        float ax = a.getX();
        float ay = a.getY();
        float bx = b.getX();
        float by = b.getY();
        float cx = c.getX();
        float cy = c.getY();
        
        float d = 2*(ax*(by-cy)+bx*(cy-ay)+cx*(ay-by));
        float centerX = ( (ax*ax+ay*ay)*(by-cy) + (bx*bx+by*by)*(cy-ay) + 
                          (cx*cx+cy*cy)*(ay-by) ) / d;
        float centerY = ( (ax*ax+ay*ay)*(cx-bx) + (bx*bx+by*by)*(ax-cx) +
                          (cx*cx+cy*cy)*(bx-ax) ) / d;
        
        return new Point(centerX, centerY);
        
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
