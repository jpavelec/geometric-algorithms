package cz.muni.fi.pa093;

import java.util.Comparator;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
public class PointAngleComparator implements Comparator<Point> {

    Point middlePoint;
        
    public PointAngleComparator(Point point) {
        this.middlePoint = point;
    }

    @Override
    public int compare(Point endPoint1, Point endPoint2) {
        Point startPoint = new Point(middlePoint.getX()-1,middlePoint.getY());
        float delta = Point.getAngle(startPoint, middlePoint, endPoint1) -
                      Point.getAngle(startPoint, middlePoint, endPoint2);
        if (delta < 0) {
            return -1;
        }
        if (delta > 0) {
            return 1;
        }
        return 0;
    }
    

}
