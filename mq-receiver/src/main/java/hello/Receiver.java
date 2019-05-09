package hello;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println("T:" + Thread.currentThread().getName());
        System.out.println(String.format("Received <%s>.", message));
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
