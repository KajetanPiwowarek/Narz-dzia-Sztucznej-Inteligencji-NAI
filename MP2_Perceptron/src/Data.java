import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Data {
    private List<String> vectors = new LinkedList<>();
    private String type;

    public Data(){ };

    public void addVector(String vector){
        vectors.add(vector);
    }
    public List<String> getVectors(){
        return vectors;
    }
    public void addType(String str){
        this.type = str;
    }
    public String getType() {
        return type;
    }

    public String toString(){
        return vectors.toString() + " | " + type;
    }
}
