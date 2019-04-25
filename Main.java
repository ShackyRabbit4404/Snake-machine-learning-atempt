import javax.swing.*;

public class Main{
    public static void main(String[] args){
        JFrame frame = new JFrame("Snake");
        Snake snake = new Snake();
        Display screen = new Display(snake);
        frame.add(screen);
        KeyboardThread keyboard = new KeyboardThread(snake);
        frame.addKeyListener(keyboard);
        frame.setSize(1020,1040);
        frame.setVisible(true);
        int[] netStruct = new int[]{24,18,18,4};
        int snakesPerGen = 10;
        GenerationManager gen = new GenerationManager(netStruct,snakesPerGen);
        int numGens = 50;
        int numTimesNetworkTested = 3;
        boolean showSnake = true;
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
                        System.out.println("Average fitness: "+avFit);
                    }
                    snake.reset();
                }
                nn.setFitness(avFit);
                System.out.println("Network Fitness: "+nn.getFitness());
            }
            gen.crossGeneration();
            if(a == 0){
                gen.best = gen.Generation.get(0);
            }
            System.out.println("Best: "+gen.best.getFitness());
        }
        snake.reset();
        while(snake.getIsAlive()){
            try{
                Thread.sleep(100);
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
            if(showSnake)
                screen.draw();
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