import javax.swing.*;
import java.awt.*;
public class Display extends JComponent{
    Snake snake;
    GenerationManager genMan;
    public Display(Snake s, GenerationManager gm){
        snake = s;
        genMan = gm;
    }
    public void draw(){
        this.repaint();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(new Color(0,0,0));
        g.fillRect(0,0,1000,1000);
        g.setColor(new Color(175,175,175));
        g.fillRect(20,20,960,960);
        for(snakeBody sb: snake.getSnake()){
            g.setColor(Color.WHITE);
            g.fillRect(sb.getX()*20,sb.getY()*20,20,20);
            g.setColor(Color.BLACK);
            g.drawRect(sb.getX()*20,sb.getY()*20,20,20);
        }
        g.setColor(Color.RED);
        g.fillRect(snake.getFoodCords()[0]*20,snake.getFoodCords()[1]*20,20,20);
        g.setColor(Color.BLACK);
        g.drawRect(snake.getFoodCords()[0]*20,snake.getFoodCords()[1]*20,20,20);
        g.drawString("Score: "+(snake.score),40,40);
        g.drawString("Generation : "+ genMan.genNum,40,60);
        g.drawString("Closest : "+ snake.closest,40,80);
    }
}