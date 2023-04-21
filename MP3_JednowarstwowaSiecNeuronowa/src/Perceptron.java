public class Perceptron {
    private double alpha = 0.2;
    private double theta = 8;
    private double[] weights;

    public Perceptron() {
        weights = fillRandom();
    }

    //Metoda do wypelnienia randomowymi liczbami wagi
    private double[] fillRandom() {
        double[] array = new double[26];
        for(int i = 0; i < array.length; i++){
            array[i] = Math.random();
        }
        return array;
    }

    //Ocenianie jezyka
    public int evaluateLearn(Data data) {
        double net = 0.0;

        for (int i = 0; i < data.getAttributes().length; i++) {
            net += data.getAttributes()[i] * weights[i];
        }
        net -= theta;

        return (net >= 0? 1:0);
    }
    public double evaluateTest(Data data) {
        double net = 0.0;

        for (int i = 0; i < data.getAttributes().length; i++) {
            net += data.getAttributes()[i] * weights[i];
        }
        net -= theta;

        return net;
    }

    //Uczenie perceptronu
    public void learn(Data data, String actualLabel) {
        int d = data.getLanguage().equals(actualLabel)? 1:0;
        int y = evaluateLearn(data);
        int error = d - y;

        if(error != 0){
            for (int i = 0; i < weights.length; i++) {
                weights[i] += alpha * error * data.getAttributes()[i];
            }

            theta -= error * alpha;
        }

    }
}