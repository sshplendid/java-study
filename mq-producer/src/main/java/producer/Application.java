package producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args).close();
    }

    @Bean
    public CommandLineRunner runner(RabbitTemplate template) {
        final String topicExchangeName = "topic1";
        final String queueName = "queue1";
        final String routingKey = "foo.bar.baz";

        return args -> {
            System.out.println("This is a producer.");
            String message = "This message is secret. sent at " + LocalDateTime.now().toString();
            template.convertAndSend(topicExchangeName, routingKey, message);
        };
    }
}
