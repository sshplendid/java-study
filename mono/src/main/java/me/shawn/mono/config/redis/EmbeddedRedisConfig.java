package me.shawn.mono.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Configuration
@Profile("local")
public class EmbeddedRedisConfig {

    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() {
        redisServer = new RedisServer(redisPort);
        redisServer.start();
        log.info("Embedded Redis Server is running on localhost: '{}'. active: '{}'", redisPort, redisServer.isActive());
    }

    @PreDestroy
    public void stopRedisServer() {
        if(redisServer != null) {
            redisServer.stop();
            log.info("Embedded Redis Server is stopped.");
        }
    }

}
