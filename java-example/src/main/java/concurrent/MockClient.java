package concurrent;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class MockClient {
    Logger log = Logger.getAnonymousLogger();

    public Integer consume(QueueMessage message) throws InterruptedException {
//        log.info("[" + message.getMessageId() + "] Some client consume message.");
        TimeUnit.MILLISECONDS.sleep(300);
        log.info("[" + message.getMessageId() + "] Finished to consume a message.");

        return message.getMessageId();
    }
}
