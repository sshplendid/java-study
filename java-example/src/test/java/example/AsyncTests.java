package example;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class AsyncTests {
    private Runnable echoAfter5Seconds = () -> {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "....Hello!");
    };

    @Test
    public void async1() throws ExecutionException, InterruptedException {
        System.out.println("==test1");
        CompletableFuture<Void> a =
                CompletableFuture.runAsync(echoAfter5Seconds)
                        .thenCompose(aVoid -> CompletableFuture.runAsync(echoAfter5Seconds))
                        .thenAcceptAsync(aVoid -> System.out.println("Accept"))
                        .exceptionally(e -> {
                            System.out.println("Exception: ");
                            System.out.println(e.getMessage());
                            return null;
                        });
        a.whenComplete((aVoid, throwable) -> System.out.println("completed."));
        a.get();
        System.out.println("<=test1");
    }

    @Test
    public void async2() throws ExecutionException, InterruptedException {
        System.out.println("Start: " + LocalDateTime.now());
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task 1 complete " + LocalDateTime.now());
        });
        CompletableFuture<Integer> task2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("task 2 complete. " + LocalDateTime.now());
            return 1;
        });

        CompletableFuture.allOf(task1, task2).whenComplete((aVoid, throwable) -> {
            System.out.println("All Completed " + LocalDateTime.now());
        }).get();
    }

    @Test
    public void async3() {
    }
}
