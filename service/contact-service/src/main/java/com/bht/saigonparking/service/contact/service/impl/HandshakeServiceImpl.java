package com.bht.saigonparking.service.contact.service.impl;

import static com.bht.saigonparking.service.contact.configuration.WebSocketConfiguration.*;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.Level;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.stereotype.Service;

import com.bht.saigonparking.api.grpc.parkinglot.ParkingLotServiceGrpc;
import com.bht.saigonparking.common.auth.SaigonParkingTokenBody;
import com.bht.saigonparking.common.exception.PostAuthenticationException;
import com.bht.saigonparking.common.util.LoggingUtil;
import com.bht.saigonparking.service.contact.service.HandshakeService;
import com.bht.saigonparking.service.contact.service.QueueService;
import com.google.protobuf.Int64Value;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Service
@RequiredArgsConstructor
public final class HandshakeServiceImpl implements HandshakeService {

    private final QueueService queueService;
    private final AbstractMessageListenerContainer messageListenerContainer;
    private final ParkingLotServiceGrpc.ParkingLotServiceStub parkingLotServiceStub;

    @Override
    public Map<String, Object> postAuthentication(@NotNull SaigonParkingTokenBody tokenBody, boolean mustConsumeFromQueue) {

        Long userId = tokenBody.getUserId();
        String userRole = tokenBody.getUserRole();
        Map<String, Object> attributes = new HashMap<>();

        attributes.put(SAIGON_PARKING_USER_ID_KEY, userId);
        attributes.put(SAIGON_PARKING_USER_ROLE_KEY, userRole);
        attributes.put(SAIGON_PARKING_USER_AUXILIARY_KEY, !mustConsumeFromQueue);

        if (mustConsumeFromQueue) {

            /* register auto-delete queue for user and start listen to it for consuming incoming message */
            Queue userQueue = queueService.registerAutoDeleteQueueForUser(userId);

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("registerAutoDeleteQueueForUser(%d)", userId));

            if ("PARKING_LOT_EMPLOYEE".equals(userRole)) {
                try {
                    parkingLotServiceStub.getParkingLotIdByParkingLotEmployeeId(Int64Value.of(userId), new StreamObserver<Int64Value>() {

                        long parkingLotId;

                        @Override
                        public void onNext(Int64Value int64Value) {
                            parkingLotId = int64Value.getValue();

                            attributes.put(SAIGON_PARKING_PARKING_LOT_ID_KEY, parkingLotId);

                            /* register auto-delete exchange for parking-lot and bind user auto-delete queue to it */
                            queueService.registerAutoDeleteExchangeForParkingLot(parkingLotId, userQueue);
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            throw new PostAuthenticationException();
                        }

                        @Override
                        public void onCompleted() {
                            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                                    String.format("registerAutoDeleteExchangeForParkingLot(%d)", parkingLotId));
                        }
                    });
                } catch (Exception exception) {

                    /* if exception occurs, immediately remove listen to queue */
                    /* as queue has no one listen to it, it will be removed (auto-delete queue) */
                    /* as exchange has no queue bind to it, it will be removed (auto-delete exchange) */
                    messageListenerContainer.removeQueues(userQueue);
                    throw new PostAuthenticationException();
                }
            }
        } else {
            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("connectedToAuxiliaryDeviceOfUser(%d)", userId)); /* auxiliary device: such as QR Scanner */
        }
        return attributes;
    }
}