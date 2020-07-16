package com.bht.saigonparking.service.contact.service.impl;

import static com.bht.saigonparking.service.contact.interceptor.WebSocketInterceptorConstraint.*;

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

import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Service
@RequiredArgsConstructor
public final class HandshakeServiceImpl implements HandshakeService {

    private final AbstractMessageListenerContainer messageListenerContainer;
    private final QueueService queueService;
    private final ParkingLotServiceGrpc.ParkingLotServiceBlockingStub parkingLotServiceBlockingStub;

    @Override
    public Map<String, Object> postAuthentication(@NotNull SaigonParkingTokenBody tokenBody) {
        Long userId = tokenBody.getUserId();
        String userRole = tokenBody.getUserRole();
        Map<String, Object> attributes = new HashMap<>();

        attributes.put(SAIGON_PARKING_USER_ID_KEY, userId);
        attributes.put(SAIGON_PARKING_USER_ROLE_KEY, userRole);

        Queue userQueue = queueService.registerAutoDeleteQueueForUser(userId);

        LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                String.format("registerAutoDeleteQueueForUser(%d)", userId));

        if ("PARKING_LOT_EMPLOYEE".equals(userRole)) {
            try {
                Long parkingLotId = parkingLotServiceBlockingStub
                        .getParkingLotIdByParkingLotEmployeeId(Int64Value.of(userId)).getValue();

                attributes.put(SAIGON_PARKING_PARKING_LOT_ID_KEY, parkingLotId);
                queueService.registerAutoDeleteExchangeForParkingLot(parkingLotId, userQueue);

                LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                        String.format("registerAutoDeleteExchangeForParkingLot(%d)", parkingLotId));

            } catch (Exception exception) {

                messageListenerContainer.removeQueues(userQueue);
                throw new PostAuthenticationException();
            }
        }
        return attributes;
    }
}