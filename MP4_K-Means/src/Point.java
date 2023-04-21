import java.util.LinkedList;
import java.util.List;

public class Point {
    private final List<Double> vectors = new LinkedList<>();

    public void addVector(Double vector){
        vectors.add(vector);
    }

    public List<Double> getVectors(){
        return vectors;
    }

    public String toString(){
        return vectors.toString();
    }
}
