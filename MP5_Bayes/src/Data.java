import java.util.*;

public class Data {
    public List<String> attributes = new LinkedList<>();

    public Data(){ }

    public void addAttribute(String attribute){
        attributes.add(attribute);
    }

    public String toString(){
        return attributes.toString();
    }
}
