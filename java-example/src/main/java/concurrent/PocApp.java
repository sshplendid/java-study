package concurrent;

import java.util.concurrent.*;

public class PocApp {

    private static final int QUEUE_CAPACITY = 10;
    public static final int THREAD_POOL_CAPACITY = 10;

    public void setQueue(BlockingQueue<QueueMessage> queue) {
        this.queue = queue;
    }

    public BlockingQueue<QueueMessage> getQueue() {
        return queue;
    }

    private BlockingQueue<QueueMessage> queue;

    public static void main(String[] args) throws InterruptedException {
        PocApp app = new PocApp();
        app.setQueue(new ArrayBlockingQueue<>(QUEUE_CAPACITY));

        Thread producer = new Thread(new Producer(app.getQueue()));
        ExecutorService service = Executors.newFixedThreadPool(THREAD_POOL_CAPACITY);
        ThreadPoolExecutor executor = (ThreadPoolExecutor) service;
        Thread consumer1 = new Thread(new Consumer(app.getQueue(), () -> producer.isAlive()));
        producer.join();
        producer.start();
        for(int i = 0; i < THREAD_POOL_CAPACITY; i++) {
            executor.execute(new Consumer(app.getQueue(), () -> producer.isAlive()));
        }

    }
}