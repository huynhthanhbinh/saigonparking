package com.bht.saigonparking.service.contact.service.impl;

import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.bht.saigonparking.api.grpc.booking.BookingServiceGrpc.BookingServiceStub;
import com.bht.saigonparking.api.grpc.booking.FinishBookingRequest;
import com.bht.saigonparking.api.grpc.contact.AvailabilityUpdateContent;
import com.bht.saigonparking.api.grpc.contact.BookingFinishContent;
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

    private final BookingServiceStub bookingServiceStub;
    private final ParkingLotServiceStub parkingLotServiceStub;
    private final WebSocketUserSessionManagement webSocketUserSessionManagement;

    @Override
    public void handleMessageSendToSystem(@NotNull SaigonParkingMessage message,
                                          @NotNull WebSocketSession session) throws InvalidProtocolBufferException {

        switch (message.getType()) {
            case AVAILABILITY_UPDATE:
                updateAvailability(message, session);
                break;
            case BOOKING_FINISH:
                finishBooking(message, session);
                break;
            default:
                break;
        }
    }

    private void updateAvailability(@NotNull SaigonParkingMessage message,
                                    @NotNull WebSocketSession session) throws InvalidProtocolBufferException {

        AvailabilityUpdateContent content = AvailabilityUpdateContent.parseFrom(message.getContent());

        String userRole = webSocketUserSessionManagement.getUserRoleFromSession(session);

        if ("PARKING_LOT_EMPLOYEE".equals(userRole)) {

            UpdateParkingLotAvailabilityRequest request = UpdateParkingLotAvailabilityRequest.newBuilder()
                    .setParkingLotId(content.getParkingLotId())
                    .setNewAvailability(content.getNewAvailability())
                    .build();

            /* Asynchronously update parking-lot availability */
            Context context = Context.current().fork();
            context.run(() -> parkingLotServiceStub.updateParkingLotAvailability(request, new StreamObserver<Empty>() {
                @Override
                public void onNext(Empty empty) {
                    // ...
                }

                @Override
                public void onError(Throwable throwable) {
                    LoggingUtil.log(Level.ERROR,
                            String.format("updateParkingLotAvailability(%d, %d)", request.getParkingLotId(), request.getNewAvailability()),
                            "Exception", throwable.getClass().getSimpleName());
                }

                @Override
                public void onCompleted() {
                    LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                            String.format("updateParkingLotAvailability(%d, %d)", request.getParkingLotId(), request.getNewAvailability()));
                }
            }));
        }
    }

    @SuppressWarnings("unused")
    private void finishBooking(@NotNull SaigonParkingMessage message,
                               @NotNull WebSocketSession session) throws InvalidProtocolBufferException {

        BookingFinishContent bookingFinishContent = BookingFinishContent.parseFrom(message.getContent());

        String userRole = webSocketUserSessionManagement.getUserRoleFromSession(session);

        if ("PARKING_LOT_EMPLOYEE".equals(userRole)) {

            FinishBookingRequest request = FinishBookingRequest.newBuilder()
                    .setBookingId(bookingFinishContent.getBookingId())
                    .build();

            /* Asynchronously update booking status to FINISHED */
            Context context = Context.current().fork();
            context.run(() -> bookingServiceStub.finishBooking(request, new StreamObserver<Empty>() {
                @Override
                public void onNext(Empty empty) {
                    // ...
                }

                @Override
                public void onError(Throwable throwable) {
                    LoggingUtil.log(Level.ERROR,
                            String.format("updateBookingStatus(%s, FINISHED)", request.getBookingId()),
                            "Exception", throwable.getClass().getSimpleName());
                }

                @Override
                public void onCompleted() {
                    LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                            String.format("updateBookingStatus(%s, FINISHED)", request.getBookingId()));
                }
            }));
        }
    }
}