package concurrent;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.concurrent.BlockingQueue;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class Consumer implements Runnable {

    private static final Logger log = Logger.getAnonymousLogger();
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
