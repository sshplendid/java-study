package hello;

import org.springframework.stereotype.Component;

@Component("topicReceiver1")
public class Receiver implements IReceiver {

    public void receiveMessage(String message) {
        System.out.println(String.format("[Topic-Recv1] Received <%s>.", message));
    }

}
