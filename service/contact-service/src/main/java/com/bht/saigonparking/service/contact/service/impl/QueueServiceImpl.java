package com.bht.saigonparking.service.contact.service.impl;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bht.saigonparking.common.constant.SaigonParkingMessageQueue;
import com.bht.saigonparking.service.contact.service.QueueService;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class QueueServiceImpl implements QueueService {

    private final AmqpAdmin amqpAdmin;
    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange contactTopicExchange;
    private final AbstractMessageListenerContainer messageListenerContainer;

    @Override
    public Queue registerAutoDeleteQueueForUser(@NotNull Long userId) {

        String queueName = SaigonParkingMessageQueue.getUserQueueName(userId);
        String routingKey = SaigonParkingMessageQueue.generateUserRoutingKey(userId);
        Queue autoDeleteUserQueue = new Queue(queueName, false, false, true);

        amqpAdmin.declareQueue(autoDeleteUserQueue);
        amqpAdmin.declareBinding(BindingBuilder.bind(autoDeleteUserQueue).to(contactTopicExchange).with(routingKey));
        messageListenerContainer.addQueues(autoDeleteUserQueue);

        return autoDeleteUserQueue;
    }

    @Override
    public void registerAutoDeleteExchangeForParkingLot(@NotNull Long parkingLotId, @NotNull Queue employeeQueue) {

        String exchangeName = SaigonParkingMessageQueue.getParkingLotExchangeName(parkingLotId);
        FanoutExchange parkingLotExchange = new FanoutExchange(exchangeName, false, true);

        amqpAdmin.declareExchange(parkingLotExchange);
        amqpAdmin.declareBinding(BindingBuilder.bind(employeeQueue).to(parkingLotExchange));
    }

    @Override
    public boolean isExchangeExist(@NotEmpty String exchangeName) {
        return rabbitTemplate.execute(channel -> {
            try {
                return channel.exchangeDeclarePassive(exchangeName);

            } catch (Exception exception) {
                return null;
            }
        }) != null;
    }

    @Override
    public boolean isQueueExist(@NotEmpty String queueName) {
        return rabbitTemplate.execute(channel -> {
            try {
                return channel.queueDeclarePassive(queueName);

            } catch (Exception exception) {
                return null;
            }
        }) != null;
    }
}