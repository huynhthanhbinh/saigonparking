package com.bht.saigonparking.service.contact.configuration;

import static com.bht.saigonparking.common.constant.SaigonParkingMessageQueue.CONTACT_EXCHANGE_NAME;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage;
import com.bht.saigonparking.service.contact.service.ContactService;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class MessageQueueConfiguration {

    private final ContactService contactService;

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

    @RabbitListener(queues = "${spring.cloud.consul.discovery.instance-id}")
    public void consumeMessageFromContactTopic(SaigonParkingMessage saigonParkingMessage) {
        contactService.consumeMessageFromQueue(saigonParkingMessage);
    }
}