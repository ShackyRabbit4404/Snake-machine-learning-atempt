public class Calculate {
    public static LinearEquation LinearRegression(double[] x, double[] y) {
        if (x.length!=y.length) {
            System.out.println("BAD INPUT");
            x = new double[]{0};
            y = new double[]{0};
        }
        double n = x.length;
        double sum1 = 0;
        for(int i=0;i<x.length;i++) {
            sum1+=(x[i]*y[i]);
        } //sum xy
        double sum2=0;
        for(double d : x) {
            sum2+=d;
        } //sum x
        double sum3=0;
        for(double d : y) {
            sum3+=d;
        } //sum y
        double sum4=0;
        for(double d : x) {
            sum4+=d*d;
        } //sum x^2
        
        double c1 = n*sum1;
        double c2 = sum2*sum3;
        double c3 = n*sum4;
        double c4 = sum2*sum2;
        
        double b = (c1-c2)/(c3-c4);
        
        double ax = 0;
        for(double d : x) {
            ax+=d;
        } //average x
        ax/=n;
        double ay = 0;
        for(double d : y) {
            ay+=d;
        } //average y
        ay/=n;
        
        double a = ay-(b*ax);
        
        LinearEquation e = new LinearEquation(a,b);
        /*
        System.out.println("sum1: " + sum1);
        System.out.println("sum2: " + sum2);
        System.out.println("sum3: " + sum3);
        System.out.println("sum4: " + sum4);
        
        System.out.println("c1: " + c1);
        System.out.println("c2: " + c2);
        System.out.println("c3: " + c3);
        System.out.println("c4: " + c4);
        
        System.out.println("a: " + e.getA());
        System.out.println("b: " + e.getB());
        System.out.println("format: a + bx");
        */
        double sum5=0;
        for(double d : y) {
            sum5+=d*d;
        } //sum y^2
        
        //double r = (c1-c2)/Math.sqrt((c3-(sum4*sum4))*((n*sum5)-sum3*sum3));        
        //double r2 = r*r;
        
        //System.out.println("r: " + r);
        //System.out.println("r2: " + r2);
        
        
        
        
        
        
        return e;
    }
}