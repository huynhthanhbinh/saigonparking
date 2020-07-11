package com.bht.saigonparking.service.contact.service.impl;

import static com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage.Type.AVAILABILITY_UPDATE;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.bht.saigonparking.api.grpc.contact.AvailabilityUpdateContent;
import com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage;
import com.bht.saigonparking.api.grpc.parkinglot.ParkingLotServiceGrpc.ParkingLotServiceStub;
import com.bht.saigonparking.api.grpc.parkinglot.UpdateParkingLotAvailabilityRequest;
import com.bht.saigonparking.service.contact.handler.WebSocketUserSessionManagement;
import com.bht.saigonparking.service.contact.service.ContactService;
import com.bht.saigonparking.service.contact.service.QueueService;
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

    private final QueueService queueService;
    private final ParkingLotServiceStub parkingLotServiceStub;
    private final WebSocketUserSessionManagement webSocketUserSessionManagement;

    @Override
    public void handleMessageSendToSystem(@NotNull SaigonParkingMessage saigonParkingMessage,
                                          @NotNull WebSocketSession session) throws InvalidProtocolBufferException {

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