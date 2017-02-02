package cz.muni.fi.pa093;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
public class Point {
    
    private float x;
    private float y;
    
    public Point() {
        this.x = 0;
        this.y = 0;
    }
    
    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
    
    public static float getAngle(Point startPoint, Point middlePoint, Point endPoint) {
        if (startPoint.equals(middlePoint) || endPoint.equals(middlePoint)) {
            return 0;
        }
        // Vector u goes from startPoint to middlePoint and v goes from middlePoint to endPoint
        float u1, u2, v1, v2;
        u1 = middlePoint.getX()-startPoint.getX();
        u2 = middlePoint.getY()-startPoint.getY();
        v1 = endPoint.getX()-middlePoint.getX();
        v2 = endPoint.getY()-middlePoint.getY();
        return (float) Math.acos(    (u1*v1+u2*v2)/
                (Math.sqrt(u1*u1+u2*u2)*Math.sqrt(v1*v1+v2*v2)));
    }
    
    /**
     * Determines clockwise direction points p1, p2, p3
     * @param p1 start point
     * @param p2 middle point
     * @param p3 end point
     * @return 0 when points lie in line, 
     *         < 0 when points lie in counterclockwise
     *         > 0 when points lie in clockwise
     */
    public static float getOrientation(Point p1, Point p2, Point p3) {
        return ( p2.getX()-p1.getX() ) * ( p3.getY()-p1.getY() ) - 
               ( p2.getY()-p1.getY() ) * ( p3.getX()-p1.getX() );
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
    public static boolean isOnLeft(Point p1, Point p2, Point p3) {
        return getOrientation(p1, p2, p3) > 0;
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
    public static boolean isOnRight(Point p1, Point p2, Point p3) {
        return getOrientation(p1, p2, p3) < 0;
    }
    
    public static float getDistance(Point p1, Point p2) {
        float diffX = p2.getX() - p1.getX();
        float diffY = p2.getY() - p1.getY();
        return (float) Math.sqrt(diffX*diffX + diffY*diffY);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Float.floatToIntBits(this.x);
        hash = 97 * hash + Float.floatToIntBits(this.y);
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
        if (!(obj instanceof Point)) {
            return false;
        }
        final Point other = (Point) obj;
        if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
            return false;
        }
        if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "["+this.x+", "+this.y+"]";
    }
    
    
    
    
    
}
