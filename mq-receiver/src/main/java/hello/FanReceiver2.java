package hello;

import org.springframework.stereotype.Component;

@Component("fanReceiver2")
public class FanReceiver2 implements IReceiver {
    @Override
    public void receiveMessage(String message) throws InterruptedException {
        Thread.sleep(500L);
        System.out.println(String.format("[F-Recv2] A Message Received: <%s>.", message));
    }
}
