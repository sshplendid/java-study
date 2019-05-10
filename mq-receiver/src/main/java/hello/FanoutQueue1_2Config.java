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


@Configuration
public class FanoutQueue1_2Config {

    private final String fanoutExchangeName = "logs";
    private final String queueName = "fanout-q1";

    @Bean
    Queue fQueue12() {
        return new Queue(queueName);
    }

    @Bean
    FanoutExchange fExchange12() {
        return new FanoutExchange(fanoutExchangeName);
    }

    @Bean
    Binding fBinding12(Queue fQueue12, FanoutExchange fExchange12) {
        return BindingBuilder.bind(fQueue12).to(fExchange12);
    }

    @Bean
    MessageListenerAdapter fListenerAdapter12(IReceiver fanReceiver12) {
        return new MessageListenerAdapter(fanReceiver12, "receiveMessage");
    }

    @Bean
    SimpleMessageListenerContainer fContainer12(ConnectionFactory connectionFactory, MessageListenerAdapter fListenerAdapter12) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(fListenerAdapter12);
        return container;
    }
}
