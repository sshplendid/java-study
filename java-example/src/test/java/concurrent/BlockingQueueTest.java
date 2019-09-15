package concurrent;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class BlockingQueueTest {
    private BlockingQueue<Integer> queue;
    private final int CAPACITY = 5;

    @Before
    public void setUp() {
        this.queue = new ArrayBlockingQueue<>(CAPACITY);
    }

    @Test(expected = IllegalStateException.class)
    public void add_throwException() {
        IntStream.range(0, CAPACITY + 1).forEach(n -> {
            queue.add(n);
        });
    }
    @Test(expected = NoSuchElementException.class)
    public void remove_throwException() {
        IntStream.range(0, CAPACITY).forEach(n -> {
            queue.add(n);
        });
        IntStream.range(0, CAPACITY + 1).forEach(n -> {
            System.out.println("poll: " + queue.remove());
        });
    }

    @Test
    public void offer() {
        IntStream.range(0, CAPACITY).forEach(n -> {
            assertTrue(queue.offer(n));
        });

        boolean result = queue.offer(-1);
        assertFalse(result);
    }

    @Test
    public void poll() {
        IntStream.range(0, CAPACITY).forEach(n -> {
            assertTrue(queue.offer(n));
        });
        IntStream.range(0, CAPACITY).forEach(n -> {
            assertTrue(queue.poll() == n);
        });
        assertNull(queue.poll());
    }

    @Test(timeout = 3000L)
    public void putWithTimeout() throws InterruptedException {
        Integer result = queue.poll(1, TimeUnit.SECONDS);
        assertNull(result);
    }

    @Test(timeout = 3000L)
    public void offerWithTimeout() throws InterruptedException {
        IntStream.range(0, CAPACITY).forEach(n -> queue.offer(n));
        boolean result = queue.offer(-1, 1, TimeUnit.SECONDS);
        assertFalse(result);
    }
}
