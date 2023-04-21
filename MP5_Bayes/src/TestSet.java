import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class TestSet {
    public List<Data> allData;

    public void getData(String fname){
        allData = new ArrayList<>();
        try{
            Files.readAllLines(Paths.get(fname)).forEach(line -> {
                String[] lineData = line.strip().split(",");
                Data inputData = new Data();
                for(int i = 0; i < lineData.length; i++){
                    inputData.addAttribute(lineData[i]);
                }
                allData.add(inputData);
            });
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
