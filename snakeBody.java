public class snakeBody{
    private int x;
    private int y;
    private snakeBody connectedTo;
    public snakeBody(int X,int Y){
        x = X;
        y = Y;
    }
    public void setConnectedTo(snakeBody ct){
        connectedTo = ct;
    }
    public void move(int X,int Y){
        if(connectedTo != null){
            connectedTo.move(x,y);
        }
        x = X;
        y = Y;
        //System.out.println("X: "+x+" Y: "+y);
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}