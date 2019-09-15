package concurrent;

import java.util.concurrent.*;

public class ThreadPoolApp {

    private static final int QUEUE_CAPACITY = 10;
    public static final int THREAD_POOL_CAPACITY = 100;

    public void setQueue(BlockingQueue<QueueMessage> queue) {
        this.queue = queue;
    }

    public BlockingQueue<QueueMessage> getQueue() {
        return queue;
    }

    private BlockingQueue<QueueMessage> queue;

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolApp app = new ThreadPoolApp();
        app.setQueue(new ArrayBlockingQueue<>(QUEUE_CAPACITY));

        Thread producer = new Thread(new Producer(app.getQueue()));
        ExecutorService service = Executors.newFixedThreadPool(THREAD_POOL_CAPACITY);
        ThreadPoolExecutor executor = (ThreadPoolExecutor) service;
        producer.join();
        producer.start();
        for(int i = 0; i < THREAD_POOL_CAPACITY; i++) {
            executor.execute(new Consumer(app.getQueue(), () -> producer.isAlive()));
        }
        executor.shutdown();
        System.out.println("Main job finished.");

    }
}