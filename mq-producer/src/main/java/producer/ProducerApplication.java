package producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class ProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args).close();
    }

    final String topicExchangeName = "topic1";

    @Bean
    CommandLineRunner runner(RabbitTemplate template) {
        final String queueName = "queue2";
        final String routingKey = "foo.bar.q2";

        return args -> {
            System.out.println("This is a producer.");
            LocalDateTime now = LocalDateTime.now();
            String message = "This message is for queue1. sent at " + now.toString();
            template.convertAndSend(topicExchangeName, routingKey, message);
            System.out.println(message);
        };
    }

    @Bean
    CommandLineRunner fanoutRunner(RabbitTemplate template) {
        return args -> {
            LocalDateTime now = LocalDateTime.now();
            String message = "this message is for fanout. sent at " + now.toString();
            String fanoutExchangeName = "logs";
            String routingKey = "abc";
            template.convertAndSend(fanoutExchangeName, routingKey, message);
            System.out.println(message);
        };
    }
}
