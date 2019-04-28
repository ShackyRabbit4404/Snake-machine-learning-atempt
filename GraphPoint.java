public class GraphPoint implements Comparable {
    private double x;
    private double y;
    public GraphPoint(double _x_, double _y_) {
        x=_x_;
        y=_y_;
    }
    public void setX(double _x_) {
        x=_x_;
    }
    public void setY(double _y_) {
        y=_y_;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public int compareTo(Object other) {
        GraphPoint o = (GraphPoint)other;
        if(this.getY()>o.getY()) {
            return 1;
        } else if (o.getY()>this.getY()) {
            return -1;
        } else {
            return 0;
        }
    }
}