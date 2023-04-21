import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class TrainSet {
    public List<Data> allData;
    public List<String> answers = new LinkedList<>();
    public List<String> values = new LinkedList<>();
    public Map<Integer, List<String>> attributesValues = new HashMap<>();

    public void getData(String fname){
        allData = new ArrayList<>();
        try{
            Files.readAllLines(Paths.get(fname)).forEach(line -> {
                String[] lineData = line.strip().split(",");
                Data inputData = new Data();
                for(int i = 0; i < lineData.length; i++){
                    inputData.addAttribute(lineData[i]);
                    if(i == lineData.length-1){
                        answers.add(lineData[i]);
                    }
                }
                allData.add(inputData);
            });

            Set<String> tmp1 = new HashSet<>(answers);
            answers = new LinkedList<>(tmp1);

            for(int i = 0; i < allData.get(0).attributes.size()-1; i++){
                for(Data data : allData){
                    values.add(data.attributes.get(i));
                }
                Set<String> tmp2 = new HashSet<>(values);
                values = new LinkedList<>(tmp2);
                attributesValues.put(i,values);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
