public class LinearEquation {
    double a;
    double b;
    public LinearEquation(double _a_, double _b_) {
        a=_a_;
        b=_b_;
    }
    public double calc(double x) {
        return a+(b*x);
    }
    public double getA() {
        return a;
    }
    public double getB() {
        return b;
    }
    public void setA(double _a_) {
        a=_a_;
    }
    public void setB(double _b_) {
        b=_b_;
    }
}