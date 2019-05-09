package me.shawn.mono.config.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Queue1Receiver {

    public void receiveMessage(String message) {
        log.info("Received <{}>.", message);
    }
}
