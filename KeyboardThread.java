import java.awt.event.*;
public class KeyboardThread extends KeyAdapter{
    Snake s;
    public KeyboardThread(Snake S){
        s = S;
    }
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP){
            s.setDirection("north");
        }
        if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT){
            s.setDirection("west");
        }
        if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN){
            s.setDirection("south");
        }
        if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT){
            s.setDirection("east");
        }
    }
}