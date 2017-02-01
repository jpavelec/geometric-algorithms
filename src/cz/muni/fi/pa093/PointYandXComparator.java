package cz.muni.fi.pa093;

import java.util.Comparator;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
public class PointYandXComparator implements Comparator<Point> {

    @Override
    public int compare(Point p1, Point p2) {
        if (Float.compare(p1.getY(), p2.getY()) > 0) {
            return 1;
        }
        if (Float.compare(p1.getY(), p2.getY()) < 0) {
            return -1;
        }
        return Float.compare(p1.getX(), p2.getX());
    }

}
