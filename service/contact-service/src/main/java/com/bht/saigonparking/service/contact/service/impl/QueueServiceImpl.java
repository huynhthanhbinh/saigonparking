package com.bht.saigonparking.service.contact.service.impl;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.Level;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bht.saigonparking.api.grpc.parkinglot.ParkingLotServiceGrpc.ParkingLotServiceStub;
import com.bht.saigonparking.common.constant.SaigonParkingMessageQueue;
import com.bht.saigonparking.common.util.LoggingUtil;
import com.bht.saigonparking.service.contact.service.QueueService;
import com.google.protobuf.Int64Value;

import io.grpc.Context;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class QueueServiceImpl implements QueueService {

    private final AmqpAdmin amqpAdmin;
    private final ParkingLotServiceStub parkingLotServiceStub;
    private final FanoutExchange contactFanoutExchange;
    private final AbstractMessageListenerContainer messageListenerContainer;

    @Override
    public void registerAutoDeleteQueueAndExchangeForUser(@NotNull Long userId, @NotEmpty String userRole) {

        String queueName = SaigonParkingMessageQueue.generateUserQueueName(userId);
        Queue autoDeleteUserQueue = new Queue(queueName, false, false, true);
        amqpAdmin.declareQueue(autoDeleteUserQueue);
        amqpAdmin.declareBinding(BindingBuilder.bind(autoDeleteUserQueue).to(contactFanoutExchange));
        messageListenerContainer.addQueues(autoDeleteUserQueue);

        if ("PARKING_LOT_EMPLOYEE".equals(userRole)) {
            Context context = Context.current();
            context.run(() -> parkingLotServiceStub.getParkingLotIdByParkingLotEmployeeId(Int64Value.of(userId),
                    new StreamObserver<Int64Value>() {
                        @Override
                        public void onNext(Int64Value int64Value) {
                            String exchangeName = SaigonParkingMessageQueue.generateParkingLotExchangeName(int64Value.getValue());
                            FanoutExchange parkingLotExchange = new FanoutExchange(exchangeName, false, true);
                            amqpAdmin.declareExchange(parkingLotExchange);
                            amqpAdmin.declareBinding(BindingBuilder.bind(autoDeleteUserQueue).to(parkingLotExchange));
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            LoggingUtil.log(Level.WARN, "SERVICE", "Fail",
                                    String.format("registerAutoDeleteQueueAndExchangeForUser(%d)", userId));
                            LoggingUtil.log(Level.ERROR, "registerAutoDeleteQueueAndExchangeForUser",
                                    "Exception", throwable.getClass().getSimpleName());
                        }

                        @Override
                        public void onCompleted() {
                            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                                    String.format("registerAutoDeleteQueueAndExchangeForUser(%d)", userId));
                        }
                    }));
        }
    }
}