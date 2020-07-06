package com.bht.saigonparking.service.contact.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage;
import com.bht.saigonparking.service.contact.service.ContactService;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

/**
 *
 * @author bht
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class ContactExchangeMessageListener implements MessageListener {

    private final ContactService contactService;

    @Override
    @SneakyThrows
    public void onMessage(Message message) {
        SaigonParkingMessage saigonParkingMessage = SaigonParkingMessage.parseFrom(message.getBody());
        contactService.consumeMessageFromQueue(saigonParkingMessage);
    }
}