import java.util.*;
public class Snake{
    private ArrayList<snakeBody> s;
    private String direction;
    private boolean isAlive;
    private int[] foodCords;
    double score;
    private double numMoves;
    private double maxNumMoves;
    double closest;
    private String prevMove;
    private double[] prevSpot;
    public Snake(){
        maxNumMoves = 400;
        reset();
    }
    public void reset(){
        s = new ArrayList<snakeBody>();
        prevSpot = new double[]{10,10};
        s.add(new snakeBody((int)(Math.random()*45+3),(int)(Math.random()*45+3)));
        s.add(new snakeBody(s.get(0).getX()-1,s.get(0).getY()));
        s.get(0).setConnectedTo(s.get(1));
        s.add(new snakeBody(s.get(1).getX()-1,s.get(1).getY()));
        s.get(1).setConnectedTo(s.get(2));
        direction = "west";
        isAlive = true; 
        foodCords = new int[2];
        setRandFoodCords();  
        score = 0;
        numMoves = 0;
        prevMove = "west";
        closest = 60;
    }
    public int[] getFoodCords(){
        return foodCords;
    }
    public double getDis(int x1, int y1,int x2,int y2){
        return Math.sqrt(Math.abs((x2-x1)*(x2-x1)) + Math.abs((y2-y1)*(y2-y1)));
    }
    public double[] getInputs(){
        double[] vals = new double[24];
        int x = 0;
        int y  = 0;
        int count = 0;
        double maxInsideDis = 67.88;
        double maxOutsideDis = 70.7;
        for(int ys = -1; ys < 2; ys ++){
            for(int xs = -1; xs < 2; xs++){
                if(!(xs == 0 && ys == 0)){
                    x = s.get(0).getX();
                    y = s.get(0).getY();
                    while(y >= 0 && y <= 50 && x >= 0 && x <= 50){                        
                        x += xs;
                        y += ys;
                        if(vals[count*3] == 0){
                            vals[count*3] = 1;
                        }
                        if(vals[count*3+1] == 0){
                            vals[count*3+1] = 1;
                        }
                        if(vals[count*3+2] == 0){
                            vals[count*3+2] = 1;
                        }
                        if(vals[count*3] == 1 && (x == 50 || y  == 50 || x == 0 || y == 0)){
                            //System.out.println("wall found");
                            vals[count*3] = Math.sqrt(Math.abs((x-s.get(0).getX())*(x-s.get(0).getX())) + Math.abs((y-s.get(0).getY())*(y-s.get(0).getY())))/maxOutsideDis;
                        }
                        else if(vals[count*3+1] == 1 && x == foodCords[0] && y == foodCords[1]){
                            //System.out.println("food found");
                            vals[count*3+1] = Math.sqrt(Math.abs((x-s.get(0).getX())*(x-s.get(0).getX())) + Math.abs((y-s.get(0).getY())*(y-s.get(0).getY())))/maxInsideDis;
                        }
                        else if(vals[count*3+2] == 1 && contains(x,y)){
                            //System.out.println("body found");
                            vals[count*3+2] = Math.sqrt(Math.abs((x-s.get(0).getX())*(x-s.get(0).getX())) + Math.abs((y-s.get(0).getY())*(y-s.get(0).getY())))/maxInsideDis;
                        }
                    }
                    count++;
                }
            }
        }
        return vals;
    }
    private boolean contains(int x, int y){
        for(snakeBody sb: s){
            if(sb.getX() == x && sb.getY() == y){
                return true;
            }
        }   
        return false;
    }
    public void setRandFoodCords(){
        int X = (int)(Math.random()*38)+1;
        int Y = (int)(Math.random()*38)+1;
        while(contains(X,Y)){
            X = (int)(Math.random()*38)+1;
            Y = (int)(Math.random()*38)+1;
        }
        foodCords = new int[]{X,Y};
    }   
    public ArrayList<snakeBody> getSnake(){
        return s;
    }
    public double getScore(){
        return score;
    }
    public void grow(){
        numMoves = 0;
        int x = s.get(s.size()-1).getX();
        int y = s.get(s.size()-1).getY();
        moveSnake();
        s.add(new snakeBody(x,y));
        s.get(s.size()-2).setConnectedTo(s.get(s.size()-1));
        setRandFoodCords();
        score += 30*(s.size()-3);
        closest = getDis(s.get(0).getX(),s.get(0).getX(),foodCords[0],foodCords[1]);
    }
    public void checkCollide(){
        numMoves ++;
        if(numMoves >= maxNumMoves){
            isAlive = false;
            //score += 10;
        }
        if(s.get(0).getX() == 0 || s.get(0).getX() == 49 || s.get(0).getY() == 0 || s.get(0).getY() == 49){
            isAlive = false;
            score -= 500*((maxNumMoves-numMoves)/maxNumMoves);
        }
        for(int i = 1; i < s.size(); i++){
            if(s.get(i).getX() == s.get(0).getX() && s.get(i).getY() == s.get(0).getY()){
                isAlive = false;
            }
        }
        if(isAlive && foodCollide()){
            grow();
        }
        else if(isAlive){
            moveSnake();
        }
    }
    public boolean foodCollide(){
        if(s.get(0).getX() == foodCords[0] && s.get(0).getY() == foodCords[1]){
            return true;
        }
        return false;
    }
    public boolean getIsAlive(){
        return isAlive;
    }
    public void setDirection(String d){
        if(d.equals("north") && s.get(1).getY() >= s.get(0).getY()){
            direction = d;
        }
        else if(d.equals("south") && s.get(1).getY() <= s.get(0).getY()){
            direction = d;
        }
        else if(d.equals("east") && s.get(1).getX() >= s.get(0).getX()){
            direction = d;
        }
        else if(d.equals("west") && s.get(1).getX() <= s.get(0).getX()){
            direction = d;
        }
    }
    private void moveSnake(){
        /*
        if(!direction.equals(prevMove)){
            score += 2;
        }
        */
        prevMove = direction;
        if(direction.equals("west")){
            s.get(0).move(s.get(0).getX()+1,s.get(0).getY()); 
        }
        else if(direction.equals("east")){
            s.get(0).move(s.get(0).getX()-1,s.get(0).getY()); 
        }
        else if(direction.equals("north")){
            s.get(0).move(s.get(0).getX(),s.get(0).getY()-1); 
        }
        else{
            s.get(0).move(s.get(0).getX(),s.get(0).getY()+1); 
        }
        /*
        if(getDis(s.get(0).getX(),s.get(0).getY(),foodCords[0],foodCords[1]) < closest){
            score += 1;
            closest = getDis(s.get(0).getX(),s.get(0).getX(),foodCords[0],foodCords[1]);
        }
        */
        if(getDis(s.get(0).getX(),s.get(0).getY(),foodCords[0],foodCords[1]) > getDis((int)prevSpot[0],(int)prevSpot[1],foodCords[0],foodCords[1])){
            score -= 10;
        }
        prevSpot = new double[]{s.get(0).getX(),s.get(0).getY()};
    }
}