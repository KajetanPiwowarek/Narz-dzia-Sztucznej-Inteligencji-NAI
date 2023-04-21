import java.util.*;

public class Main {
    public static void main(String[] args) {
//        String fnameTrain = "./train_data.txt";
//        String fnameTest = "./test_data.txt";
        String fnameTrain = "./" + args[0];
        String fnameTest = "./" + args[1];

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter a (0,1> : ");
        double a = Double.parseDouble(scanner.nextLine());

        Data wage = new Data();
        wage.addVector(String.valueOf(generateNumber()));
        wage.addVector(String.valueOf(generateNumber()));
        wage.addVector(String.valueOf(generateNumber()));
        wage.addVector(String.valueOf(generateNumber()));

        double t = generateNumber();

        DataSet trainSet = new DataSet();
        DataSet testSet = new DataSet();
        trainSet.getData(fnameTrain);
        testSet.getData(fnameTest);

        perceptronTrain(trainSet,wage,t,a);

        List<Double> results = perceptronAcc(testSet,wage,t,a);

        System.out.println("Accuracy: " + results.get(0) + "%");
        System.out.println("Accuracy first type: " + results.get(1) + "%");
        System.out.println("Accuracy second type: " + results.get(2) + "%");

        test(wage, t);
    }

    public static void test(Data wage, double t){
        Scanner scan = new Scanner(System.in);
        boolean exit = false;
        while(!exit){
            System.out.println("===================================");
            System.out.print("Please enter a (0,1> : ");
            double a = 0;
            try {
                a = Double.parseDouble(scan.nextLine());
                if(a < 0 || a >= 1){
                    throw new Exception();
                }
            }catch(Exception e){
                System.out.println("Please enter an double number from 0 to 1.");
                test(wage, t);
            }
            System.out.print("Please enter the vector(a;b;c;d): ");
            String line = "";
            String[] vectors = null;
            int result = 0;
            try {
                line = scan.nextLine();
                vectors = line.split(";");
                if(vectors.length != 4) {
                    throw new Exception();
                }
                result = perceptronType(vectors,wage,t,a);
            }catch(Exception e){
                System.out.println("Please enter correct vector.");
                test(wage, t);
            }
            System.out.println("The result: " + result);
            System.out.print("Would you like to test more (yes/no): ");
            if(scan.nextLine().equals("no"))
                exit = true;
        }
    }

    public static void perceptronTrain(DataSet trainSet, Data wage, double t, double a){
        for(Data trainData : trainSet.allData){
            double net = 0;
            int y = Integer.parseInt(trainData.getType());
            int d = Integer.parseInt(trainData.getType());
            for(int i = 0; i < trainData.getVectors().size(); i++){
                net += Double.parseDouble(trainData.getVectors().get(i)) * Double.parseDouble(wage.getVectors().get(i));
            }
            net -= t;
            if(net < 0 && y == 1){
                y = 0;
            }if(net < 0 && y == 0){
                y = 1;
            }
            //delta
            if(net < 0){
                Data newWage = new Data();
                for(int i = 0; i < wage.getVectors().size(); i++){
                    newWage.addVector(String.valueOf(Double.parseDouble(wage.getVectors().get(i)) + (d - y) * a * Double.parseDouble(trainData.getVectors().get(i))));
                }
                double newT = t - (d - y) * a;

                net = 0;
                for(int i = 0; i < trainData.getVectors().size(); i++){
                    net += Double.parseDouble(trainData.getVectors().get(i)) * Double.parseDouble(newWage.getVectors().get(i));
                }
                net -= newT;
            }
        }
    }

    public static List<Double> perceptronAcc(DataSet dataset, Data wage, double t, double a){
        List<Double> results = new ArrayList<>();
        double accuracy;
        int right = 0;
        int all = 0;
        double typeAacc;
        int typeAright = 0;
        int typeAall = 0;
        double typeBacc;
        int typeBright = 0;
        int typeBall = 0;

        for(Data data : dataset.allData){
            double net = 0;
            int y = Integer.parseInt(data.getType());
            int d = Integer.parseInt(data.getType());
            for(int i = 0; i < data.getVectors().size(); i++){
                net += Double.parseDouble(data.getVectors().get(i)) * Double.parseDouble(wage.getVectors().get(i));
            }
            net -= t;
            if(net < 0 && y == 1){
                y = 0;
            }if(net < 0 && y == 0){
                y = 1;
            }
            if(d == y){
                right++;
                all++;
                if(d == 0){
                    typeAright++;
                    typeAall++;
                }else{
                    typeBright++;
                    typeBall++;
                }
            }else{
                all++;
                if(d == 0){
                    typeAall++;
                }else{
                    typeBall++;
                }
            }
        }

        accuracy = right * 100 / all;
        typeAacc = typeAright * 100 / typeAall;
        typeBacc = typeBright * 100 / typeBall;

        results.add(accuracy);
        results.add(typeAacc);
        results.add(typeBacc);

        return results;
    }

    public static Integer perceptronType(String[] vector, Data wage, double t, double a){
        int resultType = 0;

        double net = 0;
        for (int i = 0; i < vector.length; i++) {
            net += Double.parseDouble(vector[i]) * Double.parseDouble(wage.getVectors().get(i));
        }
        net -= t;
        if (net < 0) {
            resultType = 0;
        } else {
            resultType = 1;
        }

        return resultType;
    }

    public static double generateNumber(){
        int x = (int)(Math.random() * 2);
        double y = 0;
        switch (x){
            case 0:y = Math.random() * 5;break;
            case 1:y = Math.random() * (-5);break;
        }
        return y;
    }
}


