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
public class FanoutQueue2Config {

    private final String fanoutExchangeName = "logs";
    private final String queueName = "fanout-q2";

    @Bean
    Queue fQueue2() {
        return new Queue(queueName);
    }

    @Bean
    FanoutExchange fExchange2() {
        return new FanoutExchange(fanoutExchangeName);
    }

    @Bean
    Binding fBinding2(Queue fQueue2, FanoutExchange fExchange2) {
        return BindingBuilder.bind(fQueue2).to(fExchange2);
    }

    @Bean
    MessageListenerAdapter fListenerAdapter2(IReceiver fanReceiver2) {
        return new MessageListenerAdapter(fanReceiver2, "receiveMessage");
    }

    @Bean
    SimpleMessageListenerContainer fContainer2(ConnectionFactory connectionFactory, MessageListenerAdapter fListenerAdapter2) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(fListenerAdapter2);
        return container;
    }
}
