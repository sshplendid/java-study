package hello;

import org.springframework.stereotype.Component;

@Component("fanReceiver1")
public class FanReceiver1 implements IReceiver {
    @Override
    public void receiveMessage(String message) {
        System.out.println(String.format("[F-Recv1] A Message Received: <%s>.", message));
    }
}
