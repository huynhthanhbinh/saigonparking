package com.bht.saigonparking.service.contact.configuration;

import static com.bht.saigonparking.common.constant.SaigonParkingMessageQueue.CONTACT_EXCHANGE_NAME;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.service.contact.listener.ContactExchangeMessageListener;

import lombok.AllArgsConstructor;

/**
 *
 * @author bht
 */
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class MessageQueueConfiguration {

    @Bean
    public Queue queue(@Value("${spring.cloud.consul.discovery.instance-id}") String queueName) {
        return new Queue(queueName, false, false, true); /* register an auto-delete queue on service start-up */
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(CONTACT_EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(@Autowired Queue queue, @Autowired FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    @Bean
    public SimpleMessageListenerContainer container(@Autowired ConnectionFactory connectionFactory,
                                                    @Autowired ContactExchangeMessageListener contactExchangeMessageListener,
                                                    @Value("${spring.cloud.consul.discovery.instance-id}") String queueName) {

        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueueNames(queueName);
        simpleMessageListenerContainer.setMessageListener(contactExchangeMessageListener);
        return simpleMessageListenerContainer;
    }
}