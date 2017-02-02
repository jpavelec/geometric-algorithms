package cz.muni.fi.pa093;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
public class Triangle {
    
    private Point a;
    private Point b;
    private Point c;

    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    public static List<Triangle> getTriangleWithAdjacentEdge(Triangle trian, List<Triangle> triangles) {
        List<Triangle> adjacentTriangles = new ArrayList<>();
        for (Triangle t : triangles) {
            if (haveTrianglesAdjacentEdge(trian, t)) {
                adjacentTriangles.add(t);
            }
        }
        return adjacentTriangles;
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
    
    public Point getCenterOfCircumscribedCircle() {
        return getCenterOfCircumscribedCircle(this.a, this.b, this.c);
    }
    
    // when two triangles have adjacent edge, they share exactly two vertices (points)
    private static boolean haveTrianglesAdjacentEdge(Triangle t1, Triangle t2) {
        int countSharedVertices = 0;
        for (Point pt1 : t1.getVerticesAsList()) {
            for (Point pt2 : t2.getVerticesAsList()) {
                if (pt1.equals(pt2)) {
                    countSharedVertices++;
                }
            }
        }
        if (countSharedVertices == 2) {
            return true;
        }
                
        return false;
    }
    
    public List<Point> getVerticesAsList() {
        List<Point> vertices = new ArrayList<>();
        vertices.add(this.a);
        vertices.add(this.b);
        vertices.add(this.c);
        return vertices;
    }

    public Point getA() {
        return a;
    }

    public void setA(Point a) {
        this.a = a;
    }

    public Point getB() {
        return b;
    }

    public void setB(Point b) {
        this.b = b;
    }

    public Point getC() {
        return c;
    }

    public void setC(Point c) {
        this.c = c;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.a);
        hash = 89 * hash + Objects.hashCode(this.b);
        hash = 89 * hash + Objects.hashCode(this.c);
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
        final Triangle other = (Triangle) obj;
        if (!Objects.equals(this.a, other.a)) {
            return false;
        }
        if (!Objects.equals(this.b, other.b)) {
            return false;
        }
        if (!Objects.equals(this.c, other.c)) {
            return false;
        }
        return true;
    }
    
    

}
