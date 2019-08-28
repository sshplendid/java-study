package example;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class ThreadTests {
    public static final Logger log = Logger.getAnonymousLogger();
    private final PizzaService domino = new PizzaService();

    @Test
    public void pizzaTest() {
        log.info("hungry");
        domino.order("페페로니");
        domino.deliver("성산동");
        log.info("냠냠");
    }

    @Test
    public void wrongTwoPizzasTest() {
        log.info("hungry");
        domino.order("슈프림");
        domino.order("하와이안");
        domino.deliver("올림픽로");
    }

    @Test
    public void wrongTwoPizzasTest2() {
        log.info("hungry");
        Thread order1 = new Thread(() -> domino.order("페페로니"));
        Thread order2 = new Thread(() -> domino.order("하와이안"));
        Thread deliver = new Thread(() -> domino.deliver("신천동"));
        order1.start();
        order2.start();
        deliver.start();

        log.info("Done...?");
    }

    @Test
    public void classicPizzaTest() throws InterruptedException {
        Thread order1 = new Thread(() -> domino.order("슈프림"));

        Thread order2 = new Thread(() -> domino.order("페페로니"));


        order1.start();
        order2.start();
        order1.join();
        order2.join();
        domino.deliver("잠실");

    }

    @Test
    public void modernTwoPizzaTest() throws InterruptedException {
        FutureTask<Integer> order1 = new FutureTask(() -> domino.order("페페로니"));
        FutureTask<Integer> order2 = new FutureTask(() -> domino.order("스테이크피자"));
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(order1);
        executor.execute(order2);

        while(!order1.isDone() || !order2.isDone()) {
            Thread.sleep(1000);
            log.info("tv를 보며 기다린다.");
        }
        domino.deliver("망원동");
        log.info("냠냠");
    }

    @Test
    public void modernTwoPizzaTest2() throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture<Void> pizza = CompletableFuture.allOf(
                CompletableFuture.supplyAsync(() -> domino.order("페페로니")),
                CompletableFuture.supplyAsync(() -> domino.order("슈프림")))
                .thenRunAsync(() -> domino.deliver("신천동"))
        .whenComplete((aVoid, throwable) -> log.info("냠냠"));
        while(!pizza.isDone()) {
            log.info("Waiting...");
            Thread.sleep(1000);
        }
    }
    @Test
    public void test() {
        LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
    }


}

class PizzaService {
    public static final Logger log = Logger.getAnonymousLogger();

    public int order(String pizzaType) {
        log.info("피자 주문 완료: " + pizzaType);
        int waitingTime = pizzaType.length();
        log.info("피자 만드는 중..." + pizzaType);
        try {
            TimeUnit.SECONDS.sleep(waitingTime);
            log.info("피자 완료: " + pizzaType);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return waitingTime;
    }

    public int deliver(String address) {
        log.info("배달중...: " + address);
        int waitingTime = address.length();
        try {
            TimeUnit.SECONDS.sleep(waitingTime);
            log.info("피자 배달 완료");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return waitingTime;
    }

}
