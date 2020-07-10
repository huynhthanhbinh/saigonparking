package com.bht.saigonparking.service.contact.service.impl;

import static com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage.Type.AVAILABILITY_UPDATE;

import java.io.IOException;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import com.bht.saigonparking.api.grpc.contact.AvailabilityUpdateContent;
import com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage;
import com.bht.saigonparking.api.grpc.parkinglot.ParkingLotServiceGrpc.ParkingLotServiceStub;
import com.bht.saigonparking.api.grpc.parkinglot.UpdateParkingLotAvailabilityRequest;
import com.bht.saigonparking.common.util.LoggingUtil;
import com.bht.saigonparking.service.contact.handler.WebSocketUserSessionManagement;
import com.bht.saigonparking.service.contact.service.ContactService;
import com.google.protobuf.Empty;
import com.google.protobuf.InvalidProtocolBufferException;

import io.grpc.Context;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;

/**
 *
 * @author bht
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class ContactServiceImpl implements ContactService {

    private final WebSocketUserSessionManagement webSocketUserSessionManagement;
    private final ParkingLotServiceStub parkingLotServiceStub;

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
    public void handleMessageSendToSystem(@NotNull SaigonParkingMessage saigonParkingMessage,
                                          @NotNull WebSocketSession session,
                                          @NotNull Long userId) throws InvalidProtocolBufferException {

        if (saigonParkingMessage.getType().equals(AVAILABILITY_UPDATE)) {

            AvailabilityUpdateContent content = AvailabilityUpdateContent.parseFrom(saigonParkingMessage.getContent());
            String userRole = webSocketUserSessionManagement.getUserRoleFromSession(session);

            if ("PARKING_LOT_EMPLOYEE".equals(userRole)) {

                /* Asynchronously update parking-lot availability */
                Context context = Context.current().fork();
                context.run(() -> parkingLotServiceStub.updateParkingLotAvailability(UpdateParkingLotAvailabilityRequest.newBuilder()
                                .setParkingLotId(content.getParkingLotId())
                                .setNewAvailability(content.getNewAvailability())
                                .build(),
                        new StreamObserver<Empty>() {
                            @Override
                            public void onNext(Empty empty) {
                                // ...
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                // session.doSth()
                            }

                            @Override
                            public void onCompleted() {
                                // ...
                            }
                        }));
            }
        }
    }
}