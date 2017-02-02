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
        
        if (p == null) {
            e = new Line(p2, p1);
        }
        p = getPointWithMinDelaunayDistanceOnLeft(e, points);
        Line e2 = new Line(p2, p);
        Line e3 = new Line(p, p1);
        
        List<Line> AEL = new ArrayList<>();
        List<Line> DT = new ArrayList<>();
        addToAEL(e, AEL, DT);
        addToAEL(e2, AEL, DT);
        addToAEL(e3, AEL, DT);

        int i = 0;
        while (!AEL.isEmpty()) {
            System.out.println("================================\nKolo cislo: "+i+"\nV AEL jsou hrany: ");
            for (Line l : AEL) {
                System.out.println(l);
            }
            System.out.println("V DT jsou hrany: ");
            for (Line l : DT) {
                System.out.println(l);
            }
            System.out.println("Vezmeme prvni hranu ze seznamu AEL, tj "+AEL.get(0));
            e = AEL.get(0);
            p1 = e.getStartPoint();
            p2 = e.getEndPoint();
            e = new Line(p2, p1);
            System.out.println("Bod p1: "+p1);
            System.out.println("Bod p2: "+p2);
            System.out.println("Provedeme swap hrany na "+e);
            p = getPointWithMinDelaunayDistanceOnLeft(e, points);
            if (p != null) {
                System.out.println("Bod nalevo od "+e+" s nejmensi Del. vzd. je " + p);
                e2 = new Line(p2, p);
                e3 = new Line(p, p1);
                System.out.println("Chcu pridat "+e2+" do AEL. Je e2/e2.swap() v AEL nebo DT?");
                if (!DT.contains(e2) && !AEL.contains(e2) && 
                    !DT.contains(new Line(e2.getEndPoint(), e2.getStartPoint())) &&
                    !AEL.contains(new Line(e2.getEndPoint(), e2.getStartPoint()))) {
                    addToAEL(e2, AEL, DT);
                }                
                System.out.println("Chcu pridat "+e3+" do AEL. Je e3/e3.swap() v AEL nebo DT?");
                if (!DT.contains(e3) && !AEL.contains(e3) && 
                    !DT.contains(new Line(e3.getEndPoint(), e3.getStartPoint())) &&
                    !AEL.contains(new Line(e3.getEndPoint(), e3.getStartPoint()))) {
                    System.out.println("Do AEL pridam "+e3);
                    addToAEL(e3, AEL, DT);
                }
            }
            e.swap();
            System.out.println("Do DT pridam "+e);
            //DT.add(e);
            System.out.println("Z AEL odeberu "+e);
            AEL.remove(e);
            i++;
        }
        
        return DT;
        
    }

    private static Point getPointWithMinDelaunayDistanceOnLeft(Line e, List<Point> points) {
        System.out.println("-----------------------------\nBody nalevo od hrany "+e);
        Point minOnLeft = null;
        for (Point p : points) {
            if (Point.isOnLeft(e.getStartPoint(), p, e.getEndPoint())) {
                System.out.println(p);
                if (minOnLeft == null) {
                    minOnLeft = p;
                    continue;
                }
                if (getDelaunayDistance(e, p) < getDelaunayDistance(e, minOnLeft)) {
                    minOnLeft = p;
                }
            }
        }
        System.out.println("S nejmensi Del. vzd. je " + minOnLeft+"\n-----------------------------");
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
        Line e2Swapped = new Line(e.getEndPoint(), e.getStartPoint());
        if (AEL.contains(e2Swapped)) {
            AEL.remove(e);
        } else {
            AEL.add(e);
        }
        DT.add(e);
    }


}
