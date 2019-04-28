import java.awt.*;
import javax.swing.*;
import java.util.*;
import javafx.geometry.BoundingBox;
public class StatisticsGraph extends JComponent{
    ArrayList<GraphPoint> points;
    double max_x;
    double max_y;
    double min_x;
    double min_y;
    int WIDTH=800;
    int HEIGHT=800;
    MouseThread mt;
    double value;
    GraphPoint closest;
    GraphPoint mouse;
    JFrame frame;
    public StatisticsGraph(JFrame f, int width, int height) {
        super();
        points = new ArrayList<GraphPoint>();
        max_x=0;
        max_y=0;
        min_x=0;
        min_y=0;
        value=0;
        mt = new MouseThread(this,f);
        (new Thread(mt)).start();
        frame=f;
        WIDTH=width;
        HEIGHT=height;
    }
    public void redraw() {
        repaint();
    }
    public void paintComponent(Graphics g) {
        if (points.size()>0) {
            try {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D)g;
                g2d.setRenderingHint(
                            RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(Color.RED);
                //System.out.println("points size: " + points.size());
                //System.out.println("max x: " + max_x);
                //System.out.println("max y: " + max_y);
                //System.out.println("min y : " + min_y);
                //System.out.println("max y : " + max_y);
                //System.out.println("max x : " + max_x);
                g.setColor(Color.GRAY);
                g.fillRect(0, 0, WIDTH, HEIGHT);
                g.setColor(Color.BLACK);
                g.drawString("VALUE: " + value,WIDTH-200,30);
                double av=0;
                for(GraphPoint p : points) {
                    av+=p.getY();
                }
                av/=(double)points.size();
                av=((double)((int)(av*1000)))/1000;
                g.setColor(Color.BLACK);
                g.drawString("MEAN: " + av,30,20);
                double avy = (HEIGHT*((av-(min_y/.9))/((max_y/1.1)-(min_y/.9))));
                avy+=30;
                g.setColor(Color.ORANGE);
                g.drawLine(0, HEIGHT-(int)avy, WIDTH, HEIGHT-(int)avy);
                g.setColor(Color.BLACK);
                ArrayList<GraphPoint> medianlist = new ArrayList<GraphPoint>();
                for(GraphPoint p : points) {
                    medianlist.add(p);
                }
                Collections.sort(medianlist);
                double median = 0;
                if (medianlist.size()%2==0 && medianlist.size()>0) {
                    median = (medianlist.get((int)(medianlist.size()/2))).getY();    
                } else if(medianlist.size()>1){
                    median = (((medianlist.get((int)(medianlist.size()/2))).getY())+((medianlist.get(((int)((medianlist.size()/2)-1)))).getY()))/2;
                }
                g.drawString("MEDIAN: " + median,30,40);
                double stdev=0;
                for(GraphPoint p : points) {
                    stdev+=(p.getY()-av)*(p.getY()-av);
                }
                stdev/=(double)points.size();
                stdev=Math.sqrt(stdev);
                stdev=((double)((int)(stdev*1000)))/1000;
                g.drawString("STANDARD DEVIATION: " + stdev,30,60);      
                //double maxy = (HEIGHT*((max_y-min_y)/(max_y-min_y)));
                g.setColor(Color.BLUE);
                //g.drawLine(0,(int)(HEIGHT-(HEIGHT/1.1))-30,WIDTH,(int)(HEIGHT-(HEIGHT/1.1))-30);
                //g.drawLine(0,(int)(HEIGHT-((HEIGHT)/1.1/2))-30,WIDTH,(int)(HEIGHT-((HEIGHT)/1.1/2))-30);
                //g.drawLine(0,(int)(HEIGHT-((HEIGHT)/.9)-30),WIDTH,(int)(HEIGHT-((HEIGHT)/.9)-30));
                g.setColor(Color.MAGENTA);
                double medy=0;
                if (medianlist.size()>0) {
                    medy = (HEIGHT*((((medianlist.get((int)(medianlist.size()/2))).getY())-(min_y/.9))/((max_y/1.1)-(min_y/.9))));
                }
                medy+=30;
                g.drawLine(0,HEIGHT-(int)medy,WIDTH,HEIGHT-(int)medy);
                
                double[] xlist = new double[points.size()];
                double[] ylist = new double[points.size()];
                for(int i=0;i<points.size();i++) {
                    GraphPoint p = points.get(i);
                    xlist[i]=p.getX();
                    ylist[i]=p.getY();
                }
                LinearEquation ln = Calculate.LinearRegression(xlist,ylist);
                double ln_y1 = ln.calc(0);
                double ln_y2 = ln.calc((max_x));
                g2d.setColor(Color.PINK);
                double ln_y1_scale = (HEIGHT*((ln_y1-(min_y/.9))/((max_y/1.1)-(min_y/.9))));
                double ln_y2_scale = (HEIGHT*((ln_y2-(min_y/.9))/((max_y/1.1)-(min_y/.9))));
                ln_y1_scale+=30;
                ln_y2_scale+=30;
                //System.out.println("ln_y1 : " + ln_y1_scale)  ;
                //System.out.println("ln_y2 : " + ln_y2_scale);
                g2d.drawLine(0,HEIGHT-(int)ln_y1_scale,(int)(WIDTH),HEIGHT-(int)ln_y2_scale);  
                
                g2d.setColor(new Color(255,255,255,185));
                
                double mx = mouse.getX();
                double my = mouse.getY();
                double cx=0;
                double cy=0;
                if (closest!=null) {
                    cx = closest.getX();
                    cy = closest.getY();
                } 
                
                //cy = 
                my-=27;
                cy-=30;
                //g.drawLine((int)mx, (int)my, (int)cx, (int)cy);
                double dist = Math.sqrt(((mx-cx)*(mx-cx))+((my-cy)*(my-cy)));
                if (dist<100) {
                    g2d.drawLine((int)mx, (int)my, (int)cx, (int)cy);    
                }
                g.fillOval((int)(cx-4), (int)(cy-4), 8, 8);
                
                
                if (points.size()>0) {
                    double psx = WIDTH*((points.get(0).getX())/max_x);
                    double psy = (HEIGHT*((points.get(0).getY()-(min_y/.9))/((max_y/1.1)-(min_y/.9))));
                    psy+=30;
                    for(GraphPoint p : points) {
                        double sx = WIDTH*(p.getX()/max_x);
                        //double sy = HEIGHT*((((p.getY()/max_y)))-min_y);
                        //double sy = (HEIGHT*(p.getY()/max_y))-(HEIGHT*(min_y/max_y));
                        //double sy = (HEIGHT*(p.getY()/max_y));
                        double sy = (HEIGHT*((p.getY()-(min_y/.9))/((max_y/1.1)-(min_y/.9))));
                        sy+=30;
                        //System.out.println("cy: " + p.getY());
                        //System.out.println("sx: " + sx);
                        //System.out.println("sy: " + sy);
                        //System.out.println("psx: " + psx);
                        //System.out.println("psy: " + psy);
        
                        g.setColor(Color.BLACK);
                        g.fillRect((int)((sx)-2), (int)((HEIGHT-sy)-2), 4, 4);
                        //g.setColor(Color.BLACK);
                        if (sy>psy) {
                            g2d.setColor(Color.GREEN);
                        } else if (psy>sy) {
                            g2d.setColor(Color.RED);
                        } else {
                            g2d.setColor(Color.BLACK);
                        }
                        
                        g2d.drawLine((int)sx, (int)(HEIGHT-sy), (int)psx, (int)(HEIGHT-psy));
                        psx=sx;
                        psy=sy;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void addPoint(GraphPoint gp) {
        points.add(gp);
        mt.addPoint(gp);
        if (points.size()==1) {
            min_y=(.9*gp.getY());
        }
        if (gp.getX()>(max_x/1.1)) {
            max_x=(1.1*(gp.getX()));
        }
        if (gp.getY()>(max_y/1.1)) {
            max_y=((1.1*gp.getY()));
        }
        if (gp.getY()<(min_y)/.9) {   
            min_y=(.9*gp.getY());
        }
    }
    public void addPoint(double _x_, double _y_) {
        GraphPoint gp = new GraphPoint(_x_,_y_);
        points.add(gp);
        mt.addPoint(gp);
        if (points.size()==1) {
            min_y=(.9*gp.getY());
        }
        if (gp.getX()>(max_x/1.1)) {
            max_x=(1.1*(gp.getX()));
        }
        if (gp.getY()>(max_y/1.1)) {
            max_y=((1.1*gp.getY()));
        }
        if (gp.getY()<(min_y)/.9) {   
            min_y=(.9*gp.getY());
        }
    }
    public int getWIDTH() {
        return WIDTH;
    }
    public int getHEIGHT() {
        return HEIGHT;
    }
    public double getMaxX() {
        return max_x;
    }
    public double getMinY() {
        return min_y;
    }
    public double getMaxY() {
        return max_y;
    }
    public void setValue(double v) {
        value=v;
        redraw();
    }
    public void setClosest(GraphPoint g) {
        closest=g;
    }
    public void setMouse(GraphPoint g) {
        mouse=g;
    }
}