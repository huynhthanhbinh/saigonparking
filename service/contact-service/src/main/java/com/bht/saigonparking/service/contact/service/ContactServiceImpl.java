package com.bht.saigonparking.service.contact.service;

import static com.bht.saigonparking.common.constant.SaigonParkingMessageQueue.CONTACT_EXCHANGE_NAME;
import static com.bht.saigonparking.common.constant.SaigonParkingMessageQueue.CONTACT_ROUTING_KEY;

import javax.validation.constraints.NotNull;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage;
import com.bht.saigonparking.service.contact.handler.WebSocketUserSessionManagement;

import lombok.AllArgsConstructor;

/**
 *
 * @author bht
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class ContactServiceImpl implements ContactService {

    private final RabbitTemplate rabbitTemplate;
    private final WebSocketUserSessionManagement webSocketUserSessionManagement;

    @Override
    public void publishMessageToQueue(@NotNull SaigonParkingMessage saigonParkingMessage) {
        rabbitTemplate.convertAndSend(CONTACT_EXCHANGE_NAME, CONTACT_ROUTING_KEY, saigonParkingMessage);
    }

    @Override
    public void consumeMessageFromQueue(@NotNull SaigonParkingMessage saigonParkingMessage) {

        System.out.println(String.format("%n%n%nReceive message from queue:%n%s%n%n%n", saigonParkingMessage));

        switch (saigonParkingMessage.getClassification()) {
            case CUSTOMER_MESSAGE:
                handleCustomerMessage(saigonParkingMessage);
                return;
            case PARKING_LOT_MESSAGE:
                handleParkingLotMessage(saigonParkingMessage);
                return;
            default:
                break;
        }
    }

    @Override
    public void handleMessageSendToSystem(@NotNull SaigonParkingMessage saigonParkingMessage) {

    }

    /* customer send message --> processing and/or forward message to parking-lot */
    private void handleCustomerMessage(@NotNull SaigonParkingMessage saigonParkingMessage) {

    }

    /* parking-lot send message --> processing and/or forward message to customer */
    private void handleParkingLotMessage(@NotNull SaigonParkingMessage saigonParkingMessage) {

    }
}