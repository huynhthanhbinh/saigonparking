package com.bht.saigonparking.service.contact.service.impl;

import static com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage.Classification.PARKING_LOT_MESSAGE;
import static com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage.Classification.SYSTEM_MESSAGE;
import static com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage.Type.BOOKING_PROCESSING;
import static com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage.Type.HISTORY_CHANGE;

import java.io.IOException;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import com.bht.saigonparking.api.grpc.booking.BookingServiceGrpc;
import com.bht.saigonparking.api.grpc.booking.BookingStatus;
import com.bht.saigonparking.api.grpc.booking.CreateBookingRequest;
import com.bht.saigonparking.api.grpc.booking.UpdateBookingStatusRequest;
import com.bht.saigonparking.api.grpc.contact.BookingAcceptanceContent;
import com.bht.saigonparking.api.grpc.contact.BookingCancellationContent;
import com.bht.saigonparking.api.grpc.contact.BookingFinishContent;
import com.bht.saigonparking.api.grpc.contact.BookingProcessingContent;
import com.bht.saigonparking.api.grpc.contact.BookingRejectContent;
import com.bht.saigonparking.api.grpc.contact.BookingRequestContent;
import com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage;
import com.bht.saigonparking.common.util.LoggingUtil;
import com.bht.saigonparking.service.contact.service.IntermediateService;
import com.bht.saigonparking.service.contact.service.MessagingService;
import com.google.protobuf.Empty;
import com.google.protobuf.InvalidProtocolBufferException;

import io.grpc.Context;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public final class IntermediateServiceImpl implements IntermediateService {

    private final BookingServiceGrpc.BookingServiceStub bookingServiceStub;
    private final BookingServiceGrpc.BookingServiceBlockingStub bookingServiceBlockingStub;

    @Override
    public void handleBookingRequest(@NotNull SaigonParkingMessage.Builder message,
                                     @NotNull WebSocketSession webSocketSession) throws IOException {

        BookingRequestContent.Builder bookingRequestContentBuilder = BookingRequestContent.newBuilder()
                .mergeFrom(message.getContent());

        long newBookingId = bookingServiceBlockingStub.createBooking(CreateBookingRequest.newBuilder()
                .setParkingLotId(bookingRequestContentBuilder.getParkingLotId())
                .setCustomerId(message.getSenderId())
                .setLicensePlate(bookingRequestContentBuilder.getCustomerLicense())
                .build())
                .getValue();

        BookingProcessingContent bookingProcessingContent = BookingProcessingContent.newBuilder()
                .setParkingLotId(bookingRequestContentBuilder.getParkingLotId())
                .setBookingId(newBookingId)
                .build();

        SaigonParkingMessage bookingProcessingMessage = SaigonParkingMessage.newBuilder()
                .setSenderId(0)
                .setReceiverId(message.getSenderId())
                .setClassification(SYSTEM_MESSAGE)
                .setType(BOOKING_PROCESSING)
                .setContent(bookingProcessingContent.toByteString())
                .build();

        /* attach new booking Id to forward to parking-lot */
        message.setContent(bookingRequestContentBuilder.setBookingId(newBookingId).build().toByteString());

        /* notify new booking Id to customer */
        webSocketSession.sendMessage(new BinaryMessage(bookingProcessingMessage.toByteArray()));
    }

    @Override
    public void handleBookingCancellation(@NotNull SaigonParkingMessage.Builder message,
                                          @NotNull MessagingService messagingService) throws InvalidProtocolBufferException {

        BookingCancellationContent bookingCancellationContent = BookingCancellationContent.parseFrom(message.getContent());

        UpdateBookingStatusRequest request = UpdateBookingStatusRequest.newBuilder()
                .setBookingId(bookingCancellationContent.getBookingId())
                .setStatus(BookingStatus.CANCELLED)
                .setNote(bookingCancellationContent.getReason())
                .build();

        updateBookingStatus(request, message, messagingService);
    }

    @Override
    public void handleBookingAcceptance(@NotNull SaigonParkingMessage.Builder message,
                                        @NotNull MessagingService messagingService) throws InvalidProtocolBufferException {

        BookingAcceptanceContent bookingAcceptanceContent = BookingAcceptanceContent.parseFrom(message.getContent());

        UpdateBookingStatusRequest request = UpdateBookingStatusRequest.newBuilder()
                .setBookingId(bookingAcceptanceContent.getBookingId())
                .setStatus(BookingStatus.ACCEPTED)
                .build();

        updateBookingStatus(request, message, messagingService);
    }

    @Override
    public void handleBookingReject(@NotNull SaigonParkingMessage.Builder message,
                                    @NotNull MessagingService messagingService) throws InvalidProtocolBufferException {

        BookingRejectContent bookingRejectContent = BookingRejectContent.parseFrom(message.getContent());

        UpdateBookingStatusRequest request = UpdateBookingStatusRequest.newBuilder()
                .setBookingId(bookingRejectContent.getBookingId())
                .setStatus(BookingStatus.REJECTED)
                .setNote(bookingRejectContent.getReason())
                .build();

        updateBookingStatus(request, message, messagingService);
    }

    @Override
    public void handleBookingFinish(@NotNull SaigonParkingMessage.Builder message,
                                    @NotNull MessagingService messagingService) throws InvalidProtocolBufferException {

        BookingFinishContent bookingFinishContent = BookingFinishContent.parseFrom(message.getContent());

        UpdateBookingStatusRequest request = UpdateBookingStatusRequest.newBuilder()
                .setBookingId(bookingFinishContent.getBookingId())
                .setStatus(BookingStatus.FINISHED)
                .build();

        updateBookingStatus(request, message, messagingService);
    }

    private void updateBookingStatus(@NotNull UpdateBookingStatusRequest request,
                                     @NotNull SaigonParkingMessage.Builder message,
                                     @NotNull MessagingService messagingService) {

        Context.current().run(() -> bookingServiceStub
                .updateBookingStatus(request, updateBookingStatusStreamObserver(request, message, messagingService)));
    }

    private StreamObserver<Empty> updateBookingStatusStreamObserver(@NotNull UpdateBookingStatusRequest request,
                                                                    @NotNull SaigonParkingMessage.Builder message,
                                                                    @NotNull MessagingService messagingService) {
        return new StreamObserver<Empty>() {

            @Override
            public void onNext(Empty empty) {
                if (message.getClassification().equals(PARKING_LOT_MESSAGE)) {
                    messagingService.forwardMessageToParkingLot(SaigonParkingMessage.newBuilder()
                            .setClassification(SYSTEM_MESSAGE)
                            .setType(HISTORY_CHANGE)
                            .setSenderId(0)
                            .setReceiverId(message.getSenderId())
                            .setContent(message.getContent())
                            .setTimestamp(new Timestamp(System.currentTimeMillis()).toString())
                            .build());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                LoggingUtil.log(Level.ERROR,
                        String.format("updateBookingStatus(%d, %s)", request.getBookingId(), request.getStatus()),
                        "Exception", throwable.getClass().getSimpleName());
            }

            @Override
            public void onCompleted() {
                LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                        String.format("updateBookingStatus(%d, %s)", request.getBookingId(), request.getStatus()));
            }
        };
    }
}