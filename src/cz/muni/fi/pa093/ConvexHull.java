
package cz.muni.fi.pa093;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
public class ConvexHull {
    
    public static List<Point> giftWrapping(List<Point> points) {
        List<Point> giftWrapPoints = new ArrayList<>();
        if (points.size()<3) {
            giftWrapPoints.addAll(points);
            return giftWrapPoints;
        }
        
        Point q = points.get(0);
        for (Point p : points) {
            if (Float.compare(p.getY(), q.getY())<0) {
                q = p;
            }
        }
        
        giftWrapPoints.add(q);
        
        Point px = new Point(q.getX() - 1, q.getY());
    
        Point middlePoint = q;
        Point newPoint = px;
    
        while (!newPoint.equals(q)) {
            double angle = 3.15;
            for (Point p : points) {
                if (p.equals(px) || p.equals(middlePoint)) {
                    continue;
                }

                double newAngle = Point.getAngle(px, middlePoint, p);
                if (newAngle < angle) {
                    angle = newAngle;
                    newPoint = p;
                }
            }
            giftWrapPoints.add(newPoint);
            px = middlePoint;
            middlePoint = newPoint;
        }
        
        return giftWrapPoints;
    }
    
    public static List<Point> grahamScan(List<Point> points) {
        List<Point> grahamScanPoints = new ArrayList<Point>();
        if (points.size()<3) {
            grahamScanPoints.addAll(points);
            return grahamScanPoints;
        }
        
        Point q = points.get(0);
        for (Point p : points) {
            // We'll find bottom point
            if (Float.compare(p.getY(), q.getY())>0) {
                q = p;
            } else if (Float.compare(p.getY(), q.getY()) == 0 && 
                       Float.compare(p.getX(), q.getX())>0) {
                q = p;                
            }
        }
        
        System.out.println("nejmensi: "+q);
        
        List<Point> sortedByAngle = new ArrayList<Point>();
        sortedByAngle.addAll(points);
        Collections.sort(sortedByAngle, new PointAngleComparator(q));
        System.out.println("Sorted by angle");
        for (Point p:sortedByAngle) {
            System.out.println(p);
        }
        
        grahamScanPoints.add(q);
        grahamScanPoints.add(sortedByAngle.get(1));
        
        
        
        int j = 2;
        
        while (j<sortedByAngle.size()) {
            System.out.println("j:"+j);
            Point pj = sortedByAngle.get(j);
            int length = grahamScanPoints.size();
            System.out.println("Testuju "+pj+grahamScanPoints.get(length-1)+grahamScanPoints.get(length-2));
            if (Point.getOrientation(pj, grahamScanPoints.get(length-1), 
                                      grahamScanPoints.get(length-2)) > 0) {
                grahamScanPoints.add(pj);
                j++;
            } else {
                grahamScanPoints.remove(length-1);
            }
            System.out.println("Konvex");
            for (Point p:grahamScanPoints) {
               
                System.out.println(p);
            }
        }
        return grahamScanPoints;
    }

}
