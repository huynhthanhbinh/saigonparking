package com.bht.saigonparking.service.contact.configuration;

import static com.bht.saigonparking.common.constant.SaigonParkingMessageQueue.CONTACT_EXCHANGE_NAME;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.service.contact.listener.SaigonParkingQueueMessageListener;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class MessageQueueConfiguration {

    private final ConnectionFactory connectionFactory;
    private final SaigonParkingQueueMessageListener saigonParkingQueueMessageListener;

    @Value("${spring.cloud.consul.discovery.instance-id}")
    private String queueName;


    @Bean
    public Queue queue() {
        return new Queue(queueName, false, false, true); /* register an auto-delete queue on service start-up */
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(CONTACT_EXCHANGE_NAME);
    }

    @Bean
    public Declarables declarables(@Autowired Queue queue, @Autowired FanoutExchange fanoutExchange) {
        return new Declarables(queue, fanoutExchange, BindingBuilder.bind(queue).to(fanoutExchange));
    }

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setMessageListener(saigonParkingQueueMessageListener);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setQueueNames(queueName);
        return container;
    }
}