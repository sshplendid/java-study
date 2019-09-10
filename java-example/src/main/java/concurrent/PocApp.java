package concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class PocApp {
    private BlockingQueue<QueueMessage> queue;

    public static void main(String[] args) throws InterruptedException {
        queue = new ArrayBlockingQueue<>(10);

        Thread producer = new Thread(new Producer(queue));
        Thread consumer1 = new Thread(new Consumer(queue));
        Thread consumer2 = new Thread(new Consumer(queue));
        Thread consumer3 = new Thread(new Consumer(queue));
        producer.join();
        consumer1.join();
        producer.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();



    }
}