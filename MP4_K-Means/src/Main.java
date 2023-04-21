import java.util.*;

public class Main {
    public static void main(String[] args) {
        Tools tool = new Tools();
        //ilosc klastrow
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the amount of clusters: ");
        int k = Integer.parseInt(scanner.nextLine());

        System.out.println();
        System.out.println("===========================================================================================");
        System.out.println();

        //sczytanie danych
        String fnameData = "./data.csv";
        Data data = new Data();
        data.getData(fnameData);

        //lista klastrow
        List<Cluster> clusters = new ArrayList<>();
        for (int i = 1; i <= k; i++) {
            Point centroid = tool.randomPoint(data);
            clusters.add(new Cluster("cluster_" + i, centroid));
        }

        //przydzielanie punktow do klastrow
        for (Point point : data.dataPoints) {
            double minDistance = Double.MAX_VALUE;
            String resultCluster = "";
            for (Cluster cluster : clusters) {
                double distance = tool.calcEucDistance(point, cluster.centroid);
                if (distance < minDistance) {
                    minDistance = distance;
                    resultCluster = cluster.name;
                }
            }
            for (Cluster cluster : clusters) {
                if (cluster.name.equals(resultCluster)) {
                    cluster.addPoint(point);
                }
            }
        }

        //wyslwietlenie informacji o podziale na grupy i E
        for (Cluster cluster : clusters) {
            System.out.println("Cluster: " + cluster.name);
            System.out.println(cluster.points.toString());
            cluster.E = cluster.calcE();
            System.out.println("E value = " + cluster.E);
            System.out.println("Centroid = " + cluster.centroid);
        }

        System.out.println();
        System.out.println("===========================================================================================");
        System.out.println();

        //poprawianie klastrow
        int clustersDone = 0;
        int x = 1;
        while (clustersDone <= clusters.size()) {
            //poprawianie centroidow
            for (Cluster cluster : clusters) {
                cluster.calcCentroid();
                cluster.clearCluster();
            }
            //ponowne przydzielanie punktow do klastrow z poprawionym centroidem
            for (Point point : data.dataPoints) {
                double minDistance = Double.MAX_VALUE;
                String resultCluster = "";
                for (Cluster cluster : clusters) {
                    double distance = tool.calcEucDistance(point, cluster.centroid);
                    if (distance < minDistance) {
                        minDistance = distance;
                        resultCluster = cluster.name;
                    }
                }
                for (Cluster cluster : clusters) {
                    if (cluster.name.equals(resultCluster)) {
                        cluster.addPoint(point);
                    }
                }
            }

            //sprawdzanie czy punkty w klastrach sie zmienily
            for (Cluster cluster : clusters) {
                if (!cluster.isClusterChanged()) {
                    clustersDone++;
                }
            }

            //wyslwietlenie informacji o podziale na grupy i E
            System.out.println("Change nr." + x++);
            for (Cluster cluster : clusters) {
                System.out.println("Cluster: " + cluster.name);
                System.out.println(cluster.points.toString());
                cluster.E = cluster.calcE();
                System.out.println("E value = " + cluster.E);
                System.out.println("Centroid = " + cluster.centroid);
            }
            System.out.println();
            System.out.println("===========================================================================================");
            System.out.println();
        }
    }
}
