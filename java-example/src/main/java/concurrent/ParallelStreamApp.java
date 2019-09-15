package concurrent;

public class ParallelStreamApp {
    public static void main(String[] args) {

        String poolCapacity = System.getProperty("java.util.concurrent.ForkJoinPool.common.parallelism");
        System.out.println(poolCapacity);

        MockClient client = new MockClient();
        FileReader fr = new FileReader();
        fr.readFile().parallelStream().forEach(message -> {
            try {
                client.consume(message);
            } catch (InterruptedException e) {
                System.out.print("[error] ");
                e.printStackTrace();
            }
            System.out.println("Finished to consume a message " + message.getMessageId());
        });
    }
}
