
package cz.muni.fi.pa093;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
public class Line {
    
    private Point startPoint;
    private Point endPoint;
    
    Line() {
        this.startPoint = new Point();
        this.endPoint = new Point();
    }
    
    public Line(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }
    
    public static List<Line> getPolygonFromPoints(List<Point> points) {
        List<Line> lines = new ArrayList<Line>();
        if (points.size()<2) {
            return lines;
        }
        Iterator<Point> it = points.iterator();
        Point first = it.next();
        Point actual = it.next();
        lines.add(new Line(first, actual));
        while (it.hasNext()) {
            Point beforeActual = actual;
            actual = it.next();
            lines.add(new Line(beforeActual,actual));
        }
        lines.add(new Line(actual, first));
        
        return lines;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.startPoint);
        hash = 13 * hash + Objects.hashCode(this.endPoint);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Line other = (Line) obj;
        if (!Objects.equals(this.startPoint, other.startPoint)) {
            return false;
        }
        if (!Objects.equals(this.endPoint, other.endPoint)) {
            return false;
        }
        return true;
    }
    
    


}
