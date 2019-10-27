package util.stream;

import org.junit.Test;

import java.util.Date;
import java.util.stream.IntStream;

public class ParallelStreamTests {
    @Test
    public void test1() {
        IntStream.range(0, 10).parallel().forEach(n -> {
            System.out.println(Thread.currentThread().getName() + "->" + Thread.currentThread().getId() + ": " + new Date());
        });
    }

    @Test
    public void popitExample() {
        IntStream.range(0, 10).parallel().forEach(index -> {
            System.out.println("Starting " + Thread.currentThread().getName() + ",    index=" + index + ", " + new Date());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
        });
    }
}
