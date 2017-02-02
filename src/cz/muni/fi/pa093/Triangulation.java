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
            if (Point.isOnLeft(top, points.get(i), bottom) && 
                Point.isOnLeft(top, stackTop, bottom)) {
                while (!stack.isEmpty() && Point.isOnRight(points.get(i), stackTop, stack.peek())) {
                    
                    stackTop = stack.pop();
                    lines.add(new Line(points.get(i), stackTop));
                }
                stack.push(stackTop);
                stack.push(points.get(i));
                
            } else if (Point.isOnRight(top, points.get(i), bottom) &&
                       Point.isOnRight(top, stackTop, bottom)) {
                while (!stack.isEmpty() && Point.isOnLeft(points.get(i), stackTop, stack.peek())) {
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
    
    

}
