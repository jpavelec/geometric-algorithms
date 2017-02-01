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
    
    public static List<Line> sweepLine(List<Point> inputPoints) {
        
        // Make a copy of input
        List<Point> points = new ArrayList<>(inputPoints);
        
        if (points.size() < 2) {
            return new ArrayList<>();
        }
        
        points.sort(new PointYandXComparator());
        
        Point top = points.get(0);
        Point bottom = points.get(points.size()-1);
        
        Stack<Point> stack = new Stack<>();
        List<Line> lines = new ArrayList<>();
        
        stack.push(points.get(0));
        stack.push(points.get(1));
        
        for (int i = 2; i<points.size();i++) {
            Point stackTop = stack.pop();
            if (isOnLeft(top, points.get(i), bottom) && 
                isOnLeft(top, stackTop, bottom)) {
                while (!stack.isEmpty() && isOnRight(points.get(i), stackTop, stack.peek())) {
                    
                    stackTop = stack.pop();
                    lines.add(new Line(points.get(i), stackTop));
                }
                stack.push(stackTop);
                stack.push(points.get(i));
                
            } else if (isOnRight(top, points.get(i), bottom) &&
                       isOnRight(top, stackTop, bottom)) {
                while (!stack.isEmpty() && isOnLeft(points.get(i), stackTop, stack.peek())) {
                    stackTop = stack.pop();
                    lines.add(new Line(points.get(i), stackTop));
                }
                stack.push(stackTop);
                stack.push(points.get(i));
                
            } else {
                lines.add(new Line(points.get(i), stackTop));
                while (!stack.isEmpty()) {
                    lines.add(new Line(points.get(i), stack.pop()));
                }
                stack.push(stackTop);
                stack.push(points.get(i));
            }
        }
        
        return lines;
    }
    
    /**
     * Determine point position due to vector which is formed by another two points.
     * Consider vector v from Point p1 to Point p3. When Point p2 is on left side
     * due to vector v return true. Return false otherwise.
     * 
     * @param p1 start point of vector v
     * @param p2 point to position check
     * @param p3 end point of vector v
     * @return true when Point p2 is on left side due to vector v. 
     * Return false otherwise (including points are in line).
     */
    private static boolean isOnLeft(Point p1, Point p2, Point p3) {
        return Point.getOrientation(p1, p2, p3) < 0;
    }
    
    /**
     * Determine point position due to vector which is formed by another two points.
     * Consider vector v from Point p1 to Point p3. When Point p2 is on right side
     * due to vector v return true. Return false otherwise.
     * 
     * @param p1 start point of vector v
     * @param p2 point to position check
     * @param p3 end point of vector v
     * @return true when Point p2 is on right side due to vector v.
     * Return false otherwise (including points are in line).
     */
    private static boolean isOnRight(Point p1, Point p2, Point p3) {
        return Point.getOrientation(p1, p2, p3) > 0;
    }

}
