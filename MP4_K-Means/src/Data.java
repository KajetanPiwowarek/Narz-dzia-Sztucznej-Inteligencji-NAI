import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Data {
    public List<Point> dataPoints;

    public void getData(String fname){
        dataPoints = new ArrayList<>();
        try{
            Files.readAllLines(Paths.get(fname)).forEach(line -> {
                String[] lineData = line.strip().split(";");
                Point point = new Point();
                for(int i = 0; i < lineData.length-1; i++){
                    point.addVector(Double.parseDouble(lineData[i]));
                }
                dataPoints.add(point);
            });
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public String toString(){
        return dataPoints.toString();
    }
}
