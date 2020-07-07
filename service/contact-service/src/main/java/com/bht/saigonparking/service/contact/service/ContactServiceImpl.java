package com.bht.saigonparking.service.contact.service;

import static com.bht.saigonparking.common.constant.SaigonParkingMessageQueue.CONTACT_EXCHANGE_NAME;
import static com.bht.saigonparking.common.constant.SaigonParkingMessageQueue.CONTACT_ROUTING_KEY;

import java.io.IOException;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.Level;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage;
import com.bht.saigonparking.api.grpc.parkinglot.ParkingLotServiceGrpc;
import com.bht.saigonparking.common.util.LoggingUtil;
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
    private final ParkingLotServiceGrpc.ParkingLotServiceStub parkingLotServiceStub;
    private final ParkingLotServiceGrpc.ParkingLotServiceBlockingStub parkingLotServiceBlockingStub;

    @Override
    public void publishMessageToQueue(@NotNull SaigonParkingMessage saigonParkingMessage) {
        rabbitTemplate.convertAndSend(CONTACT_EXCHANGE_NAME, CONTACT_ROUTING_KEY, saigonParkingMessage);
    }

    @Override
    public void consumeMessageFromQueue(@NotNull SaigonParkingMessage saigonParkingMessage) {
        Long receiverId = saigonParkingMessage.getReceiverId();
        Set<WebSocketSession> userSessionSet = webSocketUserSessionManagement.getAllSessionOfUser(receiverId);

        if (userSessionSet != null) {
            userSessionSet.forEach(userSession -> {
                try {
                    userSession.sendMessage(new BinaryMessage(saigonParkingMessage.toByteArray()));

                } catch (IOException e) {
                    LoggingUtil.log(Level.ERROR, String.format("forwardMessageToReceiver(%d)", receiverId),
                            "Exception", e.getMessage());
                }
            });
        }

        LoggingUtil.log(Level.ERROR, "SERVICE", String.format("forwardMessageToReceiver(%d)", receiverId),
                String.format("nSessionOfReceiver: %d", (userSessionSet != null) ? userSessionSet.size() : 0));
    }

    @Override
    public void handleMessageSendToSystem(@NotNull SaigonParkingMessage saigonParkingMessage) {
        // handle message send to system here ...
    }
}