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

    public static void getLines(KdNode node, List<Line> lines) {
        
        
        System.out.println("Hloubka "+node.getDepth());
        if (node.getDepth() % 2 == 0) {
            System.out.println("Vertikalni pres "+node.getPoint());
            lines.add(new Line(new Point(node.getPoint().getX(),-1), new Point(node.getPoint().getX(),1000)));
        } else {
            System.out.println("Horizontalni pres "+node.getPoint());
            lines.add(new Line(new Point(-1, node.getPoint().getY()), new Point(1000, node.getPoint().getY())));
        }
        if (node.getLesser() != null) {
            getLines(node.getLesser(), lines);
        }
        if (node.getGreater() != null) {
            getLines(node.getGreater(), lines);
        }
    }
    
    
}
