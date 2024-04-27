import java.util.Scanner;

public class XORGate {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        double sumError = 1;
        double [] actual = {0,1,1,0};
        double [] predicted = new double[4];
        
        NNNode node1 = new NNNode("Node 1",.5,.2,.9);
        NNNode node2 = new NNNode("Node 2",.3,.7,.6);
        NNNode node3 = new NNNode("Node 3",.9,.4,.1);
        System.out.println("Intial Weights");
        debug(node1, node2, node3);
        System.out.println("Outputs before learning");
        calculateOutput(0,0,node1,node2,node3);
        calculateOutput(0,1,node1,node2,node3);
        calculateOutput(1,0,node1,node2,node3);
        calculateOutput(1,1,node1,node2,node3);
        System.out.println();

        scanner.nextLine();
        while(sumError>=.001){
            double error =0;
            error=runIteration(1,1,0,node1,node2,node3);
            sumError = error*error;
            error=runIteration(0,1,1,node1,node2,node3);
            sumError = error*error;
            error=runIteration(1,0,1,node1,node2,node3);
            sumError = error*error;
            error=runIteration(0,0,0,node1,node2,node3);
            sumError = error*error;
            System.out.println("Error per Epoch: "+ sumError);
            debug(node1, node2, node3);
            System.out.println();

        }
        System.out.println("Weights after learning");
        debug(node1, node2, node3);

        System.out.println("Outputs after learning");
        predicted[0]=calculateOutput(0,0,node1,node2,node3);
        predicted[1]=calculateOutput(0,1,node1,node2,node3);
        predicted[2]=calculateOutput(1,0,node1,node2,node3);
        predicted[3]=calculateOutput(1,1,node1,node2,node3);
        System.out.println("Mean Sqaure Error: "+calculateMSE(predicted, actual));
        scanner.close(); 
    }
    public static double runIteration(int x1, int x2,int y, NNNode node1, NNNode node2, NNNode node3){
        double error;
        node1.setInput1(x1);
        node1.setInput2(x2);
        node2.setInput1(x1);
        node2.setInput2(x2);
        node1.calculateOutput();
        node2.calculateOutput();
        node3.setInput1(node1.getOutput());
        node3.setInput2(node2.getOutput());
        node3.calculateOutput();

        error = y-node3.getOutput();
        
        node3.calculateErrorGraident(error);
        node3.calculateDeltaWeights();

        node1.calculateErrorGraident(node3.getErrorGraident()*node3.getWeight1());
        node1.calculateDeltaWeights();

        node2.calculateErrorGraident(node3.getErrorGraident()*node3.getWeight2());
        node2.calculateDeltaWeights();

        node3.updateWeights();
        node2.updateWeights();
        node1.updateWeights();
        return error;
        
    }
    static public double calculateOutput(int x1, int x2, NNNode node1, NNNode node2, NNNode node3){

        node1.setInput1(x1);
        node1.setInput2(x2);
        node2.setInput1(x1);
        node2.setInput2(x2);
        System.out.println("Input 1: "+ x1);
        System.out.println("Input 1: "+ x2);
        node1.calculateOutput();
        node2.calculateOutput();
        node3.setInput1(node1.getOutput());
        node3.setInput2(node2.getOutput());
        node3.calculateOutput();
        System.out.println("Output: "+ node3.getOutput());
        return node3.getOutput();
    }
    static public void debug(NNNode node1,NNNode node2, NNNode node3 ){
        System.out.println("Node 1 Weight 1: "+ node1.getWeight1());
        System.out.println("Node 1 Weight 2: "+ node1.getWeight2());
        System.out.println("Node 1 Weight Bias: "+ node1.getWeightB());
        System.out.println("Node 2 Weight 1: "+ node2.getWeight1());
        System.out.println("Node 2 Weight 2: "+ node2.getWeight2());
        System.out.println("Node 2 Weight Bias: "+ node2.getWeightB());
        System.out.println("Node 3 Weight 1: "+ node3.getWeight1());
        System.out.println("Node 3 Weight 2: "+ node3.getWeight2());
        System.out.println("Node 3 Weight Bias: "+ node3.getWeightB());
    
    }
    public static double calculateMSE(double[] predicted, double[] target) {
        if (predicted.length != target.length) {
          throw new IllegalArgumentException("Arrays must have the same length");
        }
        double sum = 0.0;
        for (int i = 0; i < predicted.length; i++) {
          double error = predicted[i] - target[i];
          sum += error * error; 
        }
        return sum / predicted.length; 
      }
}