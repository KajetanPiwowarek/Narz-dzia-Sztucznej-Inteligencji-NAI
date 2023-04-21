import java.util.ArrayList;
import java.util.List;

public class Cluster {
    private Tools tool = new Tools();
    public String name;
    public Point centroid;
    public List<Point> points = new ArrayList<>();
    public double E;

    public Cluster(String name, Point centroid){
        this.name = name;
        this.centroid = centroid;
    }

    public void calcCentroid(){
        Point newCentroid = new Point();
        boolean end = false;
        int j = 0;
        while (!end) {
            double centroidVector = 0;
            for (Point point : points) {
                centroidVector += point.getVectors().get(j);
            }
            centroidVector /= points.size();
            newCentroid.addVector(centroidVector);
            j++;
            if (j == points.get(1).getVectors().size()){
                end = true;
            }
        }
        centroid = newCentroid;
    }

    public void addPoint(Point point){
        points.add(point);
    }

    public void clearCluster(){
        points = new ArrayList<>();
    }

    public boolean isClusterChanged(){
        double newE = calcE();
        if(E == newE){
            return false;
        }else{
            return true;
        }
    }

    public double calcE(){
        double allDistances = 0;
        for(Point point : points){
            allDistances += tool.calcEucDistance(point, centroid);
        }
        return allDistances / points.size();
    }
}
