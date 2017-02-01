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
    
    public static float getOrientation(Point p1, Point p2, Point p3){
        return (p2.getX()-p1.getX())*(p3.getY()-p1.getY()) - 
               (p2.getY()-p1.getY())*(p3.getX()-p1.getX());
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
