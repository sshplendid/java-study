package concurrent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader {
    private final String fileName = "test.txt";

    public List<QueueMessage> readFile() {
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(fileName))) {
            return br.lines()
                    .map(line -> {
                        String[] splitedLine = line.split(",");
                        QueueMessage message = new QueueMessage(Integer.parseInt(splitedLine[0]), splitedLine[1].trim());
                        return message;
                    })
                    .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
