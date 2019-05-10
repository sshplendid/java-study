package hello;

import org.springframework.stereotype.Component;

@Component("topicReceiver2")
public class TopicReceiver2 implements IReceiver {
    @Override
    public void receiveMessage(String message) {
        System.out.println(String.format("[Topic-Recv2] Received <%s>.", message));
    }
}
