import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataSet {
    public List<Data> allData;

    public void getData(String fname){
        allData = new ArrayList<>();
        try{
            Files.readAllLines(Paths.get(fname)).forEach(line -> {
                String[] lineData = line.replaceAll("\"","").strip().split(";");
                Data inputData = new Data();
                for(int i = 0; i < lineData.length-1; i++){
                    inputData.addVector(lineData[i]);
                }
                inputData.addType(lineData[lineData.length-1]);
                allData.add(inputData);
            });
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
