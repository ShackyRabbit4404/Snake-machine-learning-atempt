import java.util.*;
public class NeuralNetwork{
    private ArrayList<double[][]> weights;
    private int[] NetworkFrame;
    private double fitness;
    public NeuralNetwork(int[] netFrame){
        weights = new ArrayList<double[][]>();
        NetworkFrame = netFrame;
        fitness = 0;
        for(int i = 0; i < netFrame.length-1; i++){
            weights.add(new double[netFrame[i]][netFrame[i+1]]);
        }
        for(int a = 0; a < weights.size(); a++){
            for(int b = 0; b < weights.get(a).length; b++){
                for(int c = 0; c < weights.get(a)[b].length; c++){
                    weights.get(a)[b][c] = (Math.random()*2)-1;
                }
            }
        }
    }
    public double[] forwardPropagate(double[] input){
        ArrayList<double[]> inputs = new ArrayList<double[]>();
        for(int a = 0; a < NetworkFrame.length; a++){
            if(a == 0){
                inputs.add(input);
            }
            else{
                inputs.add(new double[NetworkFrame[a]]);
            }
        }
        for(int a = 0; a < NetworkFrame.length-1; a++){
            for(int b = 0; b < weights.get(a).length; b++){
                for(int c = 0; c < weights.get(a)[b].length; c++){
                    inputs.get(a+1)[c] += inputs.get(a)[b]*weights.get(a)[b][c];
                }
            }
            for(int b = 0; b < inputs.get(a+1).length; b++){
                inputs.get(a+1)[b] = rely(inputs.get(a+1)[b]);
            }
        }
        return inputs.get(inputs.size()-1);
    }
    public void setFitness(double f){
        fitness = f;
    }
    public double getFitness(){
        return fitness;
    }
    public ArrayList<double[][]> getWeights(){
        return weights;
    }
    public void setWeights(ArrayList<double[][]> w){
        weights = w;
    }
    private double sigmoid(double val){
        return 1 / (1 + Math.exp(-val));
    }
    private double rely(double ret){
        return ret;
    }
}
