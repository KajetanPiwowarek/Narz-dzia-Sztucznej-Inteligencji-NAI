import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataReader {
    //Sczytujemy z katalogow nazwe jezyka + ich teksty
    public static ArrayList<Data> parse(String dir) {
        ArrayList<Data> data = new ArrayList<>();

        try (DirectoryStream<Path> directories = Files.newDirectoryStream(Paths.get(dir))) {
            String language = null;
            for (Path directory : directories) {
                StringBuilder stringBuilder = new StringBuilder();
                if (Files.isDirectory(directory)) {
                    language = directory.getFileName().toString();
                    Stream<Path> files = Files.walk(directory);
                    for (Path file : files.filter(Files::isRegularFile).collect(Collectors.toList())) {
                        BufferedReader br = new BufferedReader(new FileReader(file.toFile()));
                        String line;
                        while (null != (line = br.readLine())) {
                            stringBuilder.append(line.toLowerCase().replaceAll("[^a-zA-Z]", ""));
                        }
                        data.add(new Data(language, stringBuilder.toString()));
                    }
                }
            }
            Collections.shuffle(data);
        } catch (Exception e) {
            e.getMessage();
        }

        return data;
    }
}