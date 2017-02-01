package cz.muni.fi.pa093;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
public class KdNode {
    private int depth;
    private Point point;
    private KdNode lesser;
    private KdNode greater;
    
    public KdNode(Point point, int depth, KdNode lesser, KdNode greater) {
        this.point = point;
        this.depth = depth;
        this.lesser = lesser;
        this.greater = greater;
    }
    
    public Point getPoint() {
        return point;
    }
    
    public KdNode getLesser() {
        return lesser;
    }
    
    public KdNode getGreater() {
        return greater;
    }
    
    public int getDepth() {
        return depth;
    }

}
