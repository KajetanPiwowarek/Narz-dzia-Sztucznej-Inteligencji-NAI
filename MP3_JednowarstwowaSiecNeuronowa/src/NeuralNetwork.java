public class NeuralNetwork {
    private String type;
    private Perceptron perceptron;

    public NeuralNetwork(String type, Perceptron perceptron) {
        this.type = type;
        this.perceptron = perceptron;
    }

    public String getType() {
        return type;
    }
    public Perceptron getPerceptron() {
        return perceptron;
    }
}
