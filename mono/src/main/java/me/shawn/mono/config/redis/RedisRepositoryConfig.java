package me.shawn.mono.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
@EnableRedisRepositories
public class RedisRepositoryConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisHost, redisPort);
    }
    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        return redisTemplate;
    }

    @Bean
    public Boolean redisConnectionTest(RedisTemplate<byte[], byte[]> redisTemplate) {
        ValueOperations<byte[], byte[]> ops = redisTemplate.opsForValue();
        String key = "Redis";
        String value = "fast";
        ops.set(key.getBytes(), value.getBytes());

        byte[] savedValue = ops.get(key.getBytes());
        String savedValueString = new String(savedValue, StandardCharsets.UTF_8);

        log.info("Redis test: value for '{}' is '{}'.", key, savedValueString);

        if(value.equals(savedValueString)) {
            redisTemplate.delete(key.getBytes());
            return true;
        } else return false;
    }
}
