package concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class MockClient {
    private static final Logger log = LoggerFactory.getLogger(MockClient.class);
    private final int TIMEOUT = 10;

    public Integer consume(QueueMessage message) throws InterruptedException {
//        log.info("[" + message.getMessageId() + "] Some client consume message.");
        TimeUnit.MILLISECONDS.sleep(TIMEOUT);
        log.info("[" + message.getMessageId() + "] Finished to consume a message.");

        return message.getMessageId();
    }
}
