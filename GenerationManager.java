import java.util.*;
public class GenerationManager{
    ArrayList<NeuralNetwork> Generation;
    //the network with the highest fitness over all
    NeuralNetwork best;
    //The structure of the network
    int[] struct;
    //Number of networks per generation
    int networksPerGen;
    double percentRandom;
    double topPercentKept;
    double weightMutationChance;
    double percentMutated;
    //Generation number
    int genNum = 1;
    public GenerationManager(int[] s,int npg){
        Generation = new ArrayList<NeuralNetwork>();
        struct = s;
        networksPerGen = npg;
        createNewGeneration();
        best = Generation.get(0);
        percentRandom = 0.1;
        topPercentKept = 0.2;
    }
    public void createNewGeneration(){
        for(int i = 0; i < networksPerGen; i++){
            Generation.add(new NeuralNetwork(struct));
        }
    }
    public void sortGen(){
        ArrayList<NeuralNetwork> newGen = new ArrayList<NeuralNetwork>();
        newGen.add(Generation.get(0));
        for(int a = 1; a < Generation.size(); a++){
            boolean isLast = true;
            for(int b = 0; b < newGen.size(); b++){
                if(Generation.get(a).getFitness() > Generation.get(b).getFitness()){
                    newGen.add(b,Generation.get(a));
                    isLast = false;
                    break;
                }
            }
            if(isLast){
                newGen.add(Generation.get(a));
            }
        }
        Generation = newGen;
    }
    public void crossGeneration(){
        sortGen();
        if(Generation.get(0).getFitness() > best.getFitness()){
            best = Generation.get(0);
            System.out.println("Best: "+best.getFitness());
        }
        ArrayList<NeuralNetwork> newGen = new ArrayList<NeuralNetwork>();
        int numNewNetworks = (int)(Generation.size()*percentRandom);
        int numKeptNetworks = (int)(Generation.size()*topPercentKept);
        //System.out.println(numNewNetworks);
        for(int a = 0; a < numKeptNetworks; a++){
            newGen.add(copyNet(Generation.get(a)));
        }
        for(int i = 0; i < numNewNetworks; i++){
            newGen.add(new NeuralNetwork(struct));
        } 
        newGen.add(copyNet(best));
        for(int i = 1; i < Generation.size()-numNewNetworks-numKeptNetworks; i++){
           newGen.add(cross(Generation.get(i%10),Generation.get((int)(Math.random()*Generation.size()))));
        }
        Generation = newGen;
        genNum += 1;
    }
    public NeuralNetwork mutate(NeuralNetwork nn){
        NeuralNetwork mutated = new NeuralNetwork(nn.getStruct());
        ArrayList<double[][]> w = new ArrayList<double[][]>();
        for(int a = 0; a < nn.getWeights().size();a++){
            w.add(nn.getWeights().get(a));
        }
        mutated.setWeights(w);
        return mutated;
    }
    public NeuralNetwork copyNet(NeuralNetwork nn){
        ArrayList<double[][]> w = new ArrayList<double[][]>();
        for(int a = 0; a < nn.getWeights().size(); a++){
            w.add(nn.getWeights().get(a));
        } 
        NeuralNetwork nn2 = new NeuralNetwork(nn.getStruct());
        nn2.setWeights(w);
        return nn2;
    }
    public NeuralNetwork cross(NeuralNetwork x, NeuralNetwork y){
        NeuralNetwork temp = new NeuralNetwork(struct);
        ArrayList<double[][]> w = temp.getWeights();
        for(int a = 0; a < w.size();a++){
            for(int b = 0; b < w.get(a).length;b++){
                for(int c = 0; c < w.get(a)[b].length;c++){
                    if(Math.random() > 0.5){
                        w.get(a)[b][c] = x.getWeights().get(a)[b][c];
                    }
                    else{
                        w.get(a)[b][c] = y.getWeights().get(a)[b][c];
                    }
                }
            }
        }
        return temp;
    }
}