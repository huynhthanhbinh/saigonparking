package com.bht.saigonparking.service.contact.configuration;

import static com.bht.saigonparking.common.constant.SaigonParkingMessageQueue.CONTACT_EXCHANGE_NAME;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(CONTACT_EXCHANGE_NAME);
    }

    @Bean
    public AbstractMessageListenerContainer simpleMessageListenerContainer() {
        DirectMessageListenerContainer container = new DirectMessageListenerContainer();
        container.setMessageListener(saigonParkingQueueMessageListener);
        container.setConnectionFactory(connectionFactory);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return container;
    }
}