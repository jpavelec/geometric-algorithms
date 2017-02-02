package cz.muni.fi.pa093;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
public class Voronoi {
    public static final float SIZE = 999999;
    
    public static List<Line> voronoi(List<Point> inputPoints) {
        List<Line> lines = new ArrayList<>();
        List<Point> points = new ArrayList<>(inputPoints);
        points.add(new Point(0, -SIZE));
        points.add(new Point(SIZE, -SIZE));
        points.add(new Point(-SIZE, -SIZE));
        points.add(new Point(SIZE, SIZE));
        List<Triangle> triangles = Delaunay.delaunayTriangles(points);
        for (Triangle t : triangles) {
            List<Triangle> adjacentTriangles = Triangle.getTriangleWithAdjacentEdge(t, triangles);
            for (Triangle adjT : adjacentTriangles) {
                lines.add(new Line(t.getCenterOfCircumscribedCircle(),
                                   adjT.getCenterOfCircumscribedCircle()));
            }
        }
        return lines;
    }

}
