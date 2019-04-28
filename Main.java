import javax.swing.*;
public class Main{
    public static void main(String[] args){
        JFrame frame = new JFrame("Snake");
        Snake snake = new Snake();
        
        int[] netStruct = new int[]{24,18,18,4};
        int snakesPerGen = 300;
        int numGens = 200;
        int numTimesNetworkTested = 4;
        int graphwidth = 400;
        int graphheight = 420;
        /*
        JFrame bestograph = new JFrame("Best score overall");
        bestograph.setSize(graphwidth,graphheight);
        bestograph.setResizable(false);
        bestograph.setVisible(true);
        bestograph.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        StatisticsGraph bog = new StatisticsGraph(bestograph,graphwidth,graphheight);
        bestograph.add(bog);
        bog.setVisible(true);
        bog.addPoint(0,0);
        bog.redraw();
        */
        GenerationManager gen = new GenerationManager(netStruct,snakesPerGen);
        Display screen = new Display(snake,gen);
        frame.add(screen);
        KeyboardThread keyboard = new KeyboardThread(snake);
        frame.addKeyListener(keyboard);
        frame.setSize(1020,1040);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        boolean showSnake = false;
        for(int a = 0; a < numGens; a++){
            for(NeuralNetwork nn: gen.Generation){
                double avFit = 0;
                for(int b = 0; b < numTimesNetworkTested; b++){
                    while(snake.getIsAlive()){
                        if(showSnake){
                            try{
                                Thread.sleep(15);
                            }
                            catch(Exception e){
                                System.out.println(e);
                            }
                        }
                        double[] outputs = nn.forwardPropagate(snake.getInputs());
                        int largestIndex = 0;
                        for(int i = 0; i < outputs.length; i++){
                            if(outputs[i] > outputs[largestIndex]){
                                largestIndex = i;
                            }
                        }
                        if(largestIndex == 0){
                            snake.setDirection("north");
                        }
                        else if(largestIndex == 1){
                            snake.setDirection("south");
                        }
                        else if(largestIndex == 2){
                            snake.setDirection("east");
                        }
                        else{
                            snake.setDirection("west");
                        }
                        snake.checkCollide();
                        if(showSnake)
                            screen.draw();
                    }
                    if(b == 0){
                        avFit = snake.getScore();
                    }
                    else{
                        avFit = (avFit + snake.getScore())/2;
                        //System.out.println("Average fitness: "+avFit);
                    }
                    snake.reset();
                }
                nn.setFitness(avFit);
                //System.out.println("Network Fitness: "+nn.getFitness());
            }
            gen.crossGeneration();
            //bog.addPoint(gen.genNum,gen.best.getFitness());
            //bog.redraw();
            //System.out.println("Best: "+gen.best.getFitness());
        }
        System.out.println("Complete training");
        while(true){
            snake.reset();
            while(snake.getIsAlive()){
                try{
                    Thread.sleep(25);
                }
                catch(Exception e){
                    System.out.println(e);
                }
                double[] outputs = gen.best.forwardPropagate(snake.getInputs());
                int largestIndex = 0;
                for(int i = 0; i < outputs.length; i++){
                    if(outputs[i] > outputs[largestIndex]){
                        largestIndex = i;
                    }
                }
                if(largestIndex == 0){
                    snake.setDirection("north");
                }
                else if(largestIndex == 1){
                    snake.setDirection("south");
                }
                else if(largestIndex == 2){
                    snake.setDirection("east");
                }
                else{
                    snake.setDirection("west");
                }
                snake.checkCollide();
                screen.draw();
                
                if(showSnake)
                    screen.draw();
            }
        }
        /*
        while(snake.getIsAlive()){
            try{
                Thread.sleep(100);
            }
            catch(Exception e){
                System.out.println(e);
            }
            snake.checkCollide();
            screen.draw();
        }
        */
    }
}