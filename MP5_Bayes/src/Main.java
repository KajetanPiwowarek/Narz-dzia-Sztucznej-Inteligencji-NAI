import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Sczytywanie danych
        String fnameTrain = "./trainingset.csv";
        TrainSet trainSet = new TrainSet();
        trainSet.getData(fnameTrain);

        String fnameTest = "./testset.csv";
        TestSet testSet = new TestSet();
        testSet.getData(fnameTest);

        //Bayes
        System.out.println("========================================= TEST =========================================");

        for (Data testdata : testSet.allData) { //bierzemy linie testowa
            List<Result> results = new LinkedList<>();
            for (int j = 0; j < trainSet.answers.size(); j++) { //bierzemy odpowiedz jaka moze byc
                int numOfEqualAnswers = 0;
                int numOfAllAnswers = 0;
                for (Data traindata : trainSet.allData) { //liczymy prawdopodobienstwo danej odpowiedzi
                    if (traindata.attributes.get(traindata.attributes.size() - 1).equals(trainSet.answers.get(j))) {
                        numOfEqualAnswers++;
                    }
                    numOfAllAnswers++;
                }
                double probabilityOfAnswer = (double) numOfEqualAnswers / numOfAllAnswers;
                for (int i = 0; i < testdata.attributes.size(); i++) { //bierzemy argument z linii testowej
                    int numOfEqualAttributes = 0;
                    int numOfAllAttributes = 0;
                    for (Data traindata : trainSet.allData) { //bierzemy linie treningowa
                        //Zliczanie wystąpień danej wartości
                        if (testdata.attributes.get(i).equals(traindata.attributes.get(i))) {
                            if (traindata.attributes.get(traindata.attributes.size() - 1).equals(trainSet.answers.get(j))) {
                                numOfEqualAttributes++;
                            }
                            numOfAllAttributes++;
                        }
                    }
                    //Laplace
                    if (numOfEqualAttributes == 0) {
                        numOfEqualAttributes++;
                        for (Map.Entry<Integer, List<String>> attributesValues : trainSet.attributesValues.entrySet()) {
                            if (attributesValues.getKey() == i) {
                                numOfAllAttributes += attributesValues.getValue().size() - 1;
                            }
                        }
                    }
                    double probabilityOfAttribute = (double) numOfEqualAttributes / numOfAllAttributes;
                    probabilityOfAnswer *= probabilityOfAttribute;
                }
                results.add(new Result(trainSet.answers.get(j), probabilityOfAnswer));
            }
            //Szukanie result max
            double max = Double.MIN_VALUE;
            String answer = "";
            for (Result result : results) {
                if (result.probability > max) {
                    max = result.probability;
                    answer = result.value;
                }
            }
            System.out.println("Dla danych: " + testdata.toString());
            System.out.println("Odpowiedz to: " + answer);
            System.out.println("========================================================================================");
        }
        test(trainSet);
    }

    public static void test(TrainSet trainSet) {
        while (true) {
            System.out.println();
            System.out.println("========================================= TEST =========================================");
            System.out.println(
                    "1. Zachmurzenie/opady - \"slonecznie\", \"zachmurzenie\", \"deszcz\", \"snieg\".\n" +
                            "2. Wiatr - \"tak\", \"nie\".\n" +
                            "3. Wilgotność - \"wysoka\", \"srednia\", \"niska\".\n" +
                            "4. Temperatura - \"wysoka\", \"srednia\", \"niska\"."
            );
            System.out.println();
            System.out.println("Wprowadz dane testu (odzielone przecinkiem \",\"):");
            Scanner scanner = new Scanner(System.in);
            String[] lineData = scanner.nextLine().toLowerCase().trim().strip().split(",");
            List<String> testData = new LinkedList<>();
            for (
                    int i = 0;
                    i < lineData.length; i++) {
                testData.add(lineData[i]);
            }
            if(testData.size() == trainSet.allData.get(0).attributes.size() - 1) {

                boolean testAttributesGood = false;
                int isAllGood = 0;
                for (int n = 0; n < testData.size(); n++) {
                    int isGood = 0;
                    for (Map.Entry<Integer, List<String>> attributesValues : trainSet.attributesValues.entrySet()) {
                        if (n == attributesValues.getKey()) {
                            for (String value : attributesValues.getValue()) {
                                if (testData.get(n).equals(value)) {
                                    isGood++;
                                }
                            }
                        }
                    }
                    if (isGood > 0) {
                        isAllGood++;
                    }
                }
                if(isAllGood == trainSet.allData.get(0).attributes.size()-1){
                    testAttributesGood = true;
                }
                if (testAttributesGood) {
                    List<Result> results = new LinkedList<>();
                    for (int j = 0; j < trainSet.answers.size(); j++) { //bierzemy odpowiedz jaka moze byc
                        int numOfEqualAnswers = 0;
                        int numOfAllAnswers = 0;
                        for (Data traindata : trainSet.allData) { //liczymy prawdopodobienstwo danej odpowiedzi
                            if (traindata.attributes.get(traindata.attributes.size() - 1).equals(trainSet.answers.get(j))) {
                                numOfEqualAnswers++;
                            }
                            numOfAllAnswers++;
                        }
                        double probabilityOfAnswer = (double) numOfEqualAnswers / numOfAllAnswers;
                        for (int i = 0; i < testData.size(); i++) { //bierzemy argument z linii testowej
                            int numOfEqualAttributes = 0;
                            int numOfAllAttributes = 0;
                            for (Data traindata : trainSet.allData) { //bierzemy linie treningowa
                                //Zliczanie wystąpień danej wartości
                                if (testData.get(i).equals(traindata.attributes.get(i))) {
                                    if (traindata.attributes.get(traindata.attributes.size() - 1).equals(trainSet.answers.get(j))) {
                                        numOfEqualAttributes++;
                                    }
                                    numOfAllAttributes++;
                                }
                            }
                            //Laplace
                            if (numOfEqualAttributes == 0) {
                                numOfEqualAttributes++;
                                for (Map.Entry<Integer, List<String>> attributesValues : trainSet.attributesValues.entrySet()) {
                                    if (attributesValues.getKey() == i) {
                                        numOfAllAttributes += attributesValues.getValue().size() - 1;
                                    }
                                }
                            }
                            double probabilityOfAttribute = (double) numOfEqualAttributes / numOfAllAttributes;
                            probabilityOfAnswer *= probabilityOfAttribute;
                        }
                        results.add(new Result(trainSet.answers.get(j), probabilityOfAnswer));
                    }
                    //Szukanie result max
                    double max = Double.MIN_VALUE;
                    String answer = "";
                    for (Result result : results) {
                        if (result.probability > max) {
                            max = result.probability;
                            answer = result.value;
                        }
                    }
                    System.out.println("Dla danych: " + testData.toString());
                    System.out.println("Odpowiedz to: " + answer);
                    System.out.println("========================================================================================");
                }
            }
            System.out.println("Chcesz zakończyć wpisz \"tak\": ");
            if(scanner.next().equals("tak")){
                return;
            }
        }
    }
}

