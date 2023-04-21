import javax.swing.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Data> test, training;
        ArrayList<NeuralNetwork> neuralNetwork;

        //Sczytujemy dane
        test = DataReader.parse("data/TestData");
        training = DataReader.parse("data/TrainingData");

        neuralNetwork = setNeuralNetwork(training);

        //Uczenie perceptronu
        for (Data trainingData : training) {
            for (NeuralNetwork network : neuralNetwork) {
                network.getPerceptron().learn(trainingData, network.getType());
            }
        }

        //Testowanie danych
        for (Data testData : test) {
            ArrayList<Results> results = new ArrayList<>();
            for (NeuralNetwork network : neuralNetwork) {
                results.add(new Results(network.getType(), network.getPerceptron().evaluateTest(testData)));
            }
            //Max selector
            boolean first = true;
            double max = 0;
            String resultType = "";
            for(Results result : results){
                if(first){
                    max = result.getNet();
                    resultType = result.getType();
                    first = false;
                }
                if (result.getNet() > max) {
                    max = result.getNet();
                    resultType = result.getType();
                }
            }
            System.out.println("The results:");
            System.out.println(testData.getLanguage() + " -> " + resultType);
            System.out.println();
        }

        System.out.println();
        System.out.println("========================================================================");
        System.out.println();

        SwingUtilities.invokeLater(() -> {
            new UI(neuralNetwork);
        });
    }

    private static ArrayList<NeuralNetwork> setNeuralNetwork(ArrayList<Data> training) {
        ArrayList<NeuralNetwork> neuralNetwork = new ArrayList<>();
        for (Data data : training)
            neuralNetwork.add(new NeuralNetwork(data.getLanguage(), new Perceptron()));
        return neuralNetwork;
    }
}
