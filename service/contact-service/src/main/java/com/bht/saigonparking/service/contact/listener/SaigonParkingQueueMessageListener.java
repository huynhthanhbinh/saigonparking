package com.bht.saigonparking.service.contact.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage;
import com.bht.saigonparking.service.contact.service.ContactService;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Component
@RequiredArgsConstructor
public final class SaigonParkingQueueMessageListener implements MessageListener {

    private final ContactService contactService;

    @Override
    public void onMessage(Message message) {
        MessageConverter messageConverter = new SimpleMessageConverter();
        SaigonParkingMessage saigonParkingMessage = (SaigonParkingMessage) messageConverter.fromMessage(message);
        contactService.consumeMessageFromQueue(saigonParkingMessage);
    }
}