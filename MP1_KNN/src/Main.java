import java.util.*;

public class Main {
    public static void main(String[] args) {
//        int k = 3;
//        String fnameTrain = "./train_data.txt";
//        String fnameTest = "./test_data.txt";
        int k = Integer.parseInt(args[0]);
        String fnameTrain = "./" + args[1];
        String fnameTest = "./" + args[2];
        DataSet trainSet = new DataSet();
        DataSet testSet = new DataSet();
        trainSet.getData(fnameTrain);
        testSet.getData(fnameTest);

        double accuracy = 0;
        int right = 0;
        int all = 0;

        for(Data testData : testSet.allData){
            Map<Double,String> distance = new HashMap<>();
            all++;
            for(Data trainData : trainSet.allData){
                Double tmp = 0.0;
                for(int i = 0; i < testData.getVectors().toArray().length; i++) {
                    tmp += (Math.pow((Double.parseDouble(testData.getVectors().get(i)) - Double.parseDouble(trainData.getVectors().get(i))), 2));
                }
                distance.put(Math.sqrt(tmp),trainData.getType());
            }
            Set<Map.Entry<Double,String>> entrySet = distance.entrySet();
            List<Map.Entry<Double,String>> list = new ArrayList<>(entrySet);
            Collections.sort(list, Comparator.comparing(Map.Entry::getKey));
            String result = compareType(list,k);
            System.out.println(result + " | " + testData.getType());
            if(result.equals(testData.getType())) {
                right++;
                accuracy = right * 100 / all;
            }
        }

        System.out.println("Accuracy: " + accuracy + "%");

        test(trainSet);
    }
    public static String compareType(List<Map.Entry<Double,String>> list, int k){
        String type;
        List<String> types = new ArrayList<>();
        for(int i = 0; i < k; i++){
            types.add(list.get(i).getValue());
        }
        Map<Integer,String> equalTypes = new HashMap<>();
        int equal = 0;
        for(String s1 : types){
            for(String s2 : types){
                if(s1.equals(s2))
                    equal++;
            }
            equalTypes.put(equal,s1);
            equal = 0;
        }
        Set<Map.Entry<Integer,String>> entrySet = equalTypes.entrySet();
        List<Map.Entry<Integer,String>> results = new ArrayList<>(entrySet);
        Collections.sort(results, Comparator.comparing(Map.Entry::getKey));
        return results.get(0).getValue();
    }
    public static void test(DataSet trainSet){
        Scanner scan = new Scanner(System.in);
        boolean exit = false;
        while(!exit){
            System.out.println("===================================");
            System.out.print("Please enter k: ");
            int k = 0;
            try {
                k = Integer.parseInt(scan.nextLine());
            }catch(Exception e){
                System.out.println("Please enter an integer number.");
                test(trainSet);
            }
            System.out.print("Please enter the vector(a;b;c;d): ");
            String line = "";
            String[] vectors = null;
            Map<Double,String> distance = new HashMap<>();
            try {
                line = scan.nextLine();
                vectors = line.split(";");
                if(vectors.length != 4) {
                    throw new Exception();
                }
            for(Data trainData : trainSet.allData){
                Double tmp = 0.0;
                for(int i = 0; i < vectors.length; i++) {
                    tmp += (Math.pow((Double.parseDouble(vectors[i]) - Double.parseDouble(trainData.getVectors().get(i))), 2));
                }
                distance.put(Math.sqrt(tmp),trainData.getType());
            }
            }catch(Exception e){
                System.out.println("Please enter correct vector.");
                test(trainSet);
            }
            Set<Map.Entry<Double,String>> entrySet = distance.entrySet();
            List<Map.Entry<Double,String>> list = new ArrayList<>(entrySet);
            Collections.sort(list, Comparator.comparing(Map.Entry::getKey));
            String result = compareType(list,k);
            System.out.println("The result: " + result);
            System.out.print("Would you like to test more (yes/no): ");
            if(scan.nextLine().equals("no"))
                exit = true;
        }
    }
}

