import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataSet {
    public List<Data> allData;
    public String typ;
    boolean firstLine = true;

    public void getData(String fname){
        allData = new ArrayList<>();
        try{
            Files.readAllLines(Paths.get(fname)).forEach(line -> {
                String[] lineData = line.replaceAll("\"","").strip().split(";");
                Data inputData = new Data();
                for(int i = 0; i < lineData.length-1; i++){
                    inputData.addVector(lineData[i]);
                }
                if(this.firstLine){
                    this.typ = lineData[lineData.length-1];
                    this.firstLine = false;
                }
                if(lineData[lineData.length-1].equals(typ)) {
                    inputData.addType("0");
                }else{
                    inputData.addType("1");
                }
                allData.add(inputData);
            });
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
