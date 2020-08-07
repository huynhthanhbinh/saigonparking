package com.bht.saigonparking.service.contact.listener;

import javax.validation.constraints.NotNull;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage;
import com.bht.saigonparking.common.constant.SaigonParkingMessageQueue;
import com.bht.saigonparking.service.contact.service.MessagingService;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SaigonParkingQueueMessageListener implements MessageListener {

    private final MessagingService messagingService;
    private final MessageConverter messageConverter;

    @Async
    @Override
    public void onMessage(@NotNull Message message) {
        /* parse receiver's user ID from message in order to process/consume message */
        String userQueueName = message.getMessageProperties().getConsumerQueue();
        Long receiverUserId = SaigonParkingMessageQueue.getUserIdFromUserQueueName(userQueueName);

        /* asynchronously consume message from queue */
        SaigonParkingMessage saigonParkingMessage = (SaigonParkingMessage) messageConverter.fromMessage(message);
        messagingService.consumeMessageFromQueue(saigonParkingMessage, receiverUserId);
    }
}