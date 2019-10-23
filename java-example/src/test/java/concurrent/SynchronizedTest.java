package concurrent;

import org.junit.Test;

public class SynchronizedTest {

    @Test
    public void threadInterference() throws InterruptedException {
        Counter counter = new Counter();
        Runnable a = () -> {
            counter.increment();
            counter.print();
//            System.out.println(Thread.currentThread().getName() + ", c:" + counter.get());
        };
        Runnable b = () -> {
            counter.decrement();
            counter.print();
//            System.out.println(Thread.currentThread().getName() + ", c:" + counter.get());
        };
        Thread tA = new Thread(a);
        Thread tB = new Thread(b);
        tA.start();
        tB.start();
        tA.join();
        tB.join();
        counter.print();
    }

    private class Counter {
        private int c = 0;

        void increment() {
            c++;
        }

        void decrement() {
            c--;
        }

        int get() {
            return c;
        }

        void print() {
            System.out.println(Thread.currentThread().getName() + ", c:" + this.get());
        }
    }
}
