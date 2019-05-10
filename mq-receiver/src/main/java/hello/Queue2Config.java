package hello;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Queue2Config {

    static final String topicExchangeName = "topic1";
    static final String queueName = "queue2";

    @Bean
    Queue queue2() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange2() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding2(Queue queue2, TopicExchange exchange2) {
        return BindingBuilder.bind(queue2).to(exchange2).with("foo.bar.q2");
    }

    @Bean
    SimpleMessageListenerContainer container2(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter2) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter2);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter2(IReceiver topicReceiver2) {
        return new MessageListenerAdapter(topicReceiver2, "receiveMessage");
    }
}
