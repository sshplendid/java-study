package concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.function.Supplier;

public class Consumer implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Consumer.class);
    private final BlockingQueue<QueueMessage> queue;
    private MockClient client;
    private Boolean progress;
    private Supplier<Boolean> supplier;

    public Consumer(BlockingQueue<QueueMessage> queue, Supplier<Boolean> supplier) {
        this.supplier = supplier;
        this.queue = queue;
        this.client = new MockClient();
        progress = true;
    }

    @Override
    public void run() {
        try {
            log.info("["+Thread.currentThread().getName() + ":" + Thread.currentThread().getId()+"]Consumer job start.");
            while(supplier.get()) {
                if(queue.isEmpty()) {
                    log.info("["+Thread.currentThread().getName() + ":" + Thread.currentThread().getId()+"]Queue is empty!");
                } else {
                    QueueMessage message = queue.take();
                    client.consume(message);
                }
            }
            log.info("["+Thread.currentThread().getName() + ":" + Thread.currentThread().getId()+"]Consumer job end.");

        } catch (InterruptedException e) {
            log.info("["+Thread.currentThread().getName() + ":" + Thread.currentThread().getId()+"]Consumer error.");
            log.info(e.getMessage());
        }
    }

}
