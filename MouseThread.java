import java.awt.*;
import javax.swing.*;
import java.util.*;
import javafx.geometry.BoundingBox;
public class MouseThread implements Runnable {
    ArrayList<GraphPoint> points;
    StatisticsGraph sg;
    boolean pause;
    JFrame frame;
    public MouseThread(StatisticsGraph s, JFrame f) {
        sg=s;
        points = new ArrayList<GraphPoint>();
        frame=f;
    }
    public void run() {
        while(true) {
            try {
                if (!pause) {
                    GraphPoint closest = null;
                    GraphPoint mouse = null;
                    GraphPoint cscale = null;
                    double cdist = Integer.MAX_VALUE;
                    for(GraphPoint p : points) {
                        if (!pause) {
                            int HEIGHT = sg.getHEIGHT();
                            int WIDTH = sg.getWIDTH();
                            double max_x = sg.getMaxX();
                            double min_y = sg.getMinY();
                            double max_y = sg.getMaxY();
                            //System.out.println("run");
                            double sx = WIDTH*(p.getX()/max_x);
                            double sy = HEIGHT-(HEIGHT*((p.getY()-(min_y/.9))/((max_y/1.1)-(min_y/.9))));
                            //sy+=30;
                            Point loc = null;
                            try{
                                loc = frame.getLocationOnScreen();
                            }
                            catch(Exception e){
                                System.out.println(e);
                            }
                            
                            double fx = loc.getX();
                            double fy = loc.getY();
                            
                            Point mp = MouseInfo.getPointerInfo().getLocation();
                            double mx = mp.getX()-fx;
                            double my = mp.getY()-fy;
                            mouse = new GraphPoint(mx,my);
                            double dist = Math.sqrt(((mx-sx)*(mx-sx))+((my-sy)*(my-sy)));
                            
                            my+=37;
                            
                            if (dist<cdist) {
                                cdist=dist;
                                closest=p;
                                cscale = new GraphPoint(sx,sy);
                            }
                            
                            
                            
                            
                            //BoundingBox pb = new BoundingBox(sx-2,HEIGHT-sy+2,4,4);
                            //System.out.println("point x : " + sx);
                            //System.out.println("point y : " + (HEIGHT-sy));
                            //Point mp = MouseInfo.getPointerInfo().getLocation();
                            //double mx = mp.getX();
                            //double my = mp.getY();
                            //System.out.println("mouse x : " + mx);
                            //System.out.println("mouse y : " + my);
                            //BoundingBox mb = new BoundingBox(mx-5,my-5,10,10);
                            //if (pb.intersects(mb)) {
                            //    sg.setValue(p.getY());
                            //    System.out.println("intersection detected");
                            //}
                            //System.out.println("dist: " + dist);
                        } else {
                            while(!pause) {
                                try {
                                    Thread.sleep(1);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    if (closest!=null) {
                        sg.setValue(closest.getY());
                        sg.setClosest(cscale);
                        sg.setMouse(mouse);
                        //System.out.println("value: " + closest.getY());
                    }
                    sg.setMouse(mouse);
                }
            } catch (Exception e) { 
                e.printStackTrace();
            }
            try {
                Thread.sleep(50);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void addPoint(GraphPoint gp) {
        pause=true;
        points.add(new GraphPoint(gp.getX(),gp.getY()));
        try {
            Thread.sleep(5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pause=false;
    }
}