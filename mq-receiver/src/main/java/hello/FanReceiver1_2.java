package hello;

import org.springframework.stereotype.Component;

@Component("fanReceiver12")
public class FanReceiver1_2 implements IReceiver {
    @Override
    public void receiveMessage(String message) {
        System.out.println(String.format("[F-Recv1_2] A Message Received: <%s>.", message));
    }
}
