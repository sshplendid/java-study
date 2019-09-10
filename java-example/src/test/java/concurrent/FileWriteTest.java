package concurrent;

import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

public class FileWriteTest {
    @Test
    public void make10MillionFile() {
        String path = System.getProperty("user.dir");
        String fileName = "test.txt";

        System.out.println(path + "/" + fileName);
        File file = new File(path + "/" + fileName);
        try (FileWriter fw = new FileWriter(file)) {
            IntStream.range(0, 100000).forEach(n -> {
                try {
                    fw.write(n + ", This message written at " + LocalDateTime.now() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
