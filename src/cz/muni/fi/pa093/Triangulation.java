package cz.muni.fi.pa093;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
public class Triangulation {
    
    public static List<Line> sweepLine(List<Point> points) {
        /*
        Point bottom = new Point(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
        Point top = new Point(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
        
        for (Point p : points) {
            if (p.getY() < top.getY()) {
                top = p;
            }
            if (p.getY() > bottom.getY()) {
                bottom = p;
            }
        }
        
        System.out.println("Top & bottom"+top+bottom);
        
        List<Point> leftPath = new ArrayList<>();
        List<Point> rightPath = new ArrayList<>();
        
        for (Point p : points) {
            if (isOnLeft(top, p, bottom)) {
                leftPath.add(p);
            } else {
                rightPath.add(p);
            }
        }
        
        System.out.println("Prava cesta ");
        rightPath.sort(new PointYandXComparator());
        for (Point p : rightPath) System.out.println(p);
        
        System.out.println("Leva cesta ");
        leftPath.sort(new PointYandXComparator());
        for (Point p : leftPath) System.out.println(p);
        
        return null;*/
        
        List<Point> trianPoints = new ArrayList<>(points);
        
        Collections.copy(trianPoints, points);
        
        if (trianPoints.size() < 2) {
            return new ArrayList<Line>();
        }
        
        trianPoints.sort(new PointYandXComparator());
        
        Point top = trianPoints.get(0);
        Point bottom = trianPoints.get(trianPoints.size()-1);
        
        Stack<Point> stack = new Stack<Point>();
        List<Line> lines = new ArrayList<Line>();
        
        stack.push(trianPoints.get(0));
        stack.push(trianPoints.get(1));
        
        for (int i = 2; i<trianPoints.size();i++) {
            System.out.println("Obsah zasobniku:");
            for (int k = 0; k<stack.size();k++){
                System.out.println(stack.get(k));
            }
            Point stackTop = stack.pop();
            if (isOnLeft(top, trianPoints.get(i), bottom) && 
                isOnLeft(top, stackTop, bottom)) {
                System.out.println("Body "+trianPoints.get(i)+stackTop+" jsou oba vlevo");
  //              System.out.println("Bod "+stackTop+" je nalevo od vektoru "+points.get(i)+stack.peek());
                while (!stack.isEmpty() && isOnRight(trianPoints.get(i), stackTop, stack.peek())) {
                    
                    stackTop = stack.pop();
                    lines.add(new Line(trianPoints.get(i), stackTop));
                    System.out.println("Pridam diagonalu "+trianPoints.get(i)+stackTop);
                }
                stack.push(stackTop);
                stack.push(trianPoints.get(i));
                
            } else if (isOnRight(top, trianPoints.get(i), bottom) &&
                       isOnRight(top, stackTop, bottom)) {
                System.out.println("Body "+trianPoints.get(i)+stackTop+" jsou oba vpravo");
//                System.out.println("Bod "+stackTop+" je napravo od vektoru "+points.get(i)+stack.peek());
                while (!stack.isEmpty() && isOnLeft(trianPoints.get(i), stackTop, stack.peek())) {
                    stackTop = stack.pop();
                    lines.add(new Line(trianPoints.get(i), stackTop));
                    System.out.println("Pridam diagonalu "+trianPoints.get(i)+stackTop);
                }
                stack.push(stackTop);
                stack.push(trianPoints.get(i));
                
            } else {
                System.out.println("Body "+trianPoints.get(i)+stackTop+" jsou kazdy na jine ceste");
                System.out.println("Pridam diagonalu "+trianPoints.get(i)+stackTop);
                lines.add(new Line(trianPoints.get(i), stackTop));
                while (!stack.isEmpty()) {
                    System.out.println("Pridam diagonalu "+trianPoints.get(i)+stack.peek());
                    lines.add(new Line(trianPoints.get(i), stack.pop()));
                }
                stack.push(stackTop);
                stack.push(trianPoints.get(i));
            }
        }
        
        
        return lines;
    }
    
    private static boolean isOnLeft(Point p1, Point p2, Point p3) {
        return Point.getOrientation(p1, p2, p3) < 0;
    }
    
    private static boolean isOnRight(Point p1, Point p2, Point p3) {
        return Point.getOrientation(p1, p2, p3) > 0;
    }

}
