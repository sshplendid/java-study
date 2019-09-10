package concurrent;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class Producer implements Runnable {

    private final Logger log = Logger.getAnonymousLogger();
    private final BlockingQueue<QueueMessage> queue;
    private final FileReader fileReader;

    public Producer(BlockingQueue<QueueMessage> queue) {
        this.queue = queue;
        fileReader = new FileReader();
    }

    @Override
    public void run() {
        List<QueueMessage> messageList = fileReader.readFile();
        messageList.forEach(message -> {
            try {
                queue.put(message);
            } catch (InterruptedException e) {
                log.info("[" + message.getMessageId() + "] Failed to put a message from list to queue.");
                e.printStackTrace();
            }
        });
    }
}
