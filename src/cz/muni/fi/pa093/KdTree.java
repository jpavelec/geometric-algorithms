package cz.muni.fi.pa093;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
public class KdTree {
    
    public static KdNode buildKdTree(List<Point> points, int depth) {
        
        if (points.isEmpty()) {
            return null;
        }
        
        if (points.size() == 1) {
            return new KdNode(points.get(0), depth, null, null);
        }
        
        List<Point> lesserPoints = new ArrayList<>();
        List<Point> greaterPoints = new ArrayList<>();
        Point median;
        
        if (depth % 2 == 0) {
            System.out.println("Hloubka " + depth+", seradim podle X");
            Collections.sort(points, new PointXandYComparator());
        } else {
            System.out.println("Hloubka " + depth+", seradim podle Y");
            Collections.sort(points, new PointYandXComparator());
        }
        
        median = points.get(points.size()/2);
        System.out.println("Median: "+median);
        lesserPoints.addAll(points.subList(0, points.indexOf(median)));
        greaterPoints.addAll(points.subList(points.indexOf(median) + 1, points.size()));
        System.out.println("Body mensi nez median:");
        for (Point p : lesserPoints) System.out.println(p);
        System.out.println("Body vetsi nez median:");
        for (Point p : greaterPoints) System.out.println(p);
        
        KdNode v = new KdNode(median,
                              depth,
                              buildKdTree(lesserPoints, depth + 1),
                              buildKdTree(greaterPoints, depth + 1));
        
        return v;
        
    }

    public static List<Line> getLines(KdNode node, float minX, float minY, float maxX, float maxY) {
        
        if (node == null ) {
            return Collections.EMPTY_LIST;
        }
        
        List<Line> lines = new ArrayList<>();
        System.out.println("Hloubka "+node.getDepth());
        if (node.getDepth() % 2 == 0) {
            System.out.println("Primka vertikalne pres bod " + node.getPoint());
            Point startPointOfLine = new Point(node.getPoint().getX(), minY);
            Point endPointOfLine = new Point(node.getPoint().getX(), maxY);
            lines.add(new Line(startPointOfLine, endPointOfLine));
            if (node.getLesser() != null) {
                lines.addAll(getLines(node.getLesser(), minX, minY, node.getPoint().getX(), maxY));
            }
            if (node.getGreater() !=null) {
                lines.addAll(getLines(node.getGreater(), node.getPoint().getX(), minY, maxX, maxY));
            }
            return lines;
        } else {
            System.out.println("Primka horizontalne pres bod " + node.getPoint());
            Point startPointOfLine = new Point(minX, node.getPoint().getY());
            Point endPointOfLine = new Point(maxX, node.getPoint().getY());
            lines.add(new Line(startPointOfLine, endPointOfLine));
            if (node.getLesser() != null) {
                lines.addAll(getLines(node.getLesser(), minX, minY, maxX, node.getPoint().getY()));
            }
            if (node.getGreater() !=null) {
                lines.addAll(getLines(node.getGreater(), minX, node.getPoint().getY(), maxX, maxY));
            }
            return lines;
            
        }
        
    }
    
    
}
