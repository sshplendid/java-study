package concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    private final Logger log = LoggerFactory.getLogger(Producer.class);
    private final BlockingQueue<QueueMessage> queue;
    private final FileReader fileReader;

    public Producer(BlockingQueue<QueueMessage> queue) {
        this.queue = queue;
        fileReader = new FileReader();
    }

    @Override
    public void run() {
        log.info(Thread.currentThread().getName() + Thread.currentThread().getId() + " Producer job started.");
        List<QueueMessage> messageList = fileReader.readFile();
        messageList.forEach(message -> {
            try {
                queue.put(message);
            } catch (InterruptedException e) {
                log.info("[" + message.getMessageId() + "] Failed to put a message from list to queue.");
                e.printStackTrace();
            }
        });
        log.info(Thread.currentThread().getName() + Thread.currentThread().getId() + " Producer job finished.");
    }
}
