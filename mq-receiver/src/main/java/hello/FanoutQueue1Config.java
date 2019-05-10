package hello;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;


@Configuration
public class FanoutQueue1Config {

    private final String fanoutExchangeName = "logs";
    private final String queueName = "fanout-q1";

    @Bean
    Queue fQueue1() {
        return new Queue(queueName);
    }

    @Bean
    FanoutExchange fExchange1() {
        return new FanoutExchange(fanoutExchangeName);
    }

    @Bean
    Binding fBinding1(Queue fQueue1, FanoutExchange fExchange1) {
        return BindingBuilder.bind(fQueue1).to(fExchange1);
    }

    @Bean
    MessageListenerAdapter fListenerAdapter1(IReceiver fanReceiver1) {
        return new MessageListenerAdapter(fanReceiver1, "receiveMessage");
    }

    @Bean
    SimpleMessageListenerContainer fContainer1(ConnectionFactory connectionFactory, MessageListenerAdapter fListenerAdapter1) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(fListenerAdapter1);
        return container;
    }
}
