
public class NNNode {
  private String name;
  private double weight1;
  private double weight2;
  private double weightB;

  private double deltaWeight1;
  private double deltaWeight2;
  private double deltaWeightB;

  private double input1;
  
  private double input2;
  private double output;
  private double errorGraident;

  // Constructor to initialize weights
  public NNNode(String name,double weight1, double weight2, double weightB)  {
    this.name = name;
    this.weight1 = weight1;
    this.weight2 = weight2;
    this.weightB = weightB;
  }

  // Setters for weights
  public void setInput1(double input1) {
    this.input1 = input1;
  }

  public void setInput2(double input2) {
    this.input2 = input2;
  }
  public void setWeight1(double weight1) {
      this.weight1 = weight1;
  }
  
  public void setWeight2(double weight2) {
      this.weight2 = weight2;
  }
  
  public double getWeight1() {
    return this.weight1;
  }

  public double getWeight2() {
    return this.weight2;
  }
  public double getWeightB() {
    return this.weightB;
  }
  // Calculate the weighted sum of inputs
  public void calculateOutput() {
    //this.output = Math.max(0.0, ((weight1 * input1) + (weight2 * input2) + weightB));
    this.output = 1.0 / (1.0 + Math.exp(-((weight1 * input1) + (weight2 * input2) + weightB)));
  }
  public double getOutput() {
    return this.output;
  }
  public void calculateErrorGraident(double error){
    this.errorGraident =  this.output*(1-this.output)*error;
  }
  public double getErrorGraident() {
    return this.errorGraident;
  }
  public void calculateDeltaWeights(){
    deltaWeight1 = .1*this.input1*this.errorGraident;
    deltaWeight2 = .1*this.input2*this.errorGraident;
    deltaWeightB = .1*1*this.errorGraident;
  }
  public void updateWeights(){
    this.weight1 +=deltaWeight1;
    this.weight2 +=deltaWeight2;
    this.weightB +=deltaWeightB;
  }
  public void debug(){
    System.out.println(name);
    System.out.println("input 1: "+ input1);
    System.out.println("input 2: "+ input2);
    System.out.println("Weight 1: "+ weight1);
    System.out.println("Weight 2: "+ weight2);
    System.out.println("Weight B: "+ weightB);
    System.out.println("output: "+ output);
    System.out.println("DWeight 1: "+ deltaWeight1);
    System.out.println("DWeight 2: "+ deltaWeight2);
    System.out.println("DWeight B: "+ deltaWeightB);
  } 
}