package me.shawn.mono.config.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("mq")
@Component
@Slf4j
public class Queue1Receiver {

    public void receiveMessage(String message) {
        log.info("[Fanout Queue] A message received <{}>.", message);
    }
}
