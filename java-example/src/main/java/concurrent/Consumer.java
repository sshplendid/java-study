package concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class Consumer implements Runnable {

    private static final Logger log = Logger.getAnonymousLogger();
    private final BlockingQueue<QueueMessage> queue;
    private MockClient client;
    private Boolean progress;

    public Consumer(BlockingQueue<QueueMessage> queue) {
        this.queue = queue;
        this.client = new MockClient();
        progress = true;
    }

    @Override
    public void run() {
        try {
            log.info("["+Thread.currentThread().getName() + "-" + Thread.currentThread().getId()+"]Consumer job start.");
            while(progress) {
                if(queue.isEmpty()) {
                    progress = false;
                    log.info("["+Thread.currentThread().getName() + "-" + Thread.currentThread().getId()+"]Queue is empty!");
                    return;
                }
                QueueMessage message = queue.take();
                client.consume(message);
            }
            log.info("["+Thread.currentThread().getName() + "-" + Thread.currentThread().getId()+"]Consumer job end.");

        } catch (InterruptedException e) {
            log.info("["+Thread.currentThread().getName() + "-" + Thread.currentThread().getId()+"]Consumer error.");
            log.info(e.getMessage());
        }
    }
}
