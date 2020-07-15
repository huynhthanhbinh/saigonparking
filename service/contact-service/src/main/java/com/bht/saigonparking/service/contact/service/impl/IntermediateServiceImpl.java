package com.bht.saigonparking.service.contact.service.impl;

import static com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage.Classification.SYSTEM_MESSAGE;
import static com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage.Type.BOOKING_PROCESSING;

import java.io.IOException;

import javax.validation.constraints.NotNull;

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
import com.bht.saigonparking.service.contact.handler.WebSocketUserSessionManagement;
import com.bht.saigonparking.service.contact.service.IntermediateService;
import com.google.protobuf.Empty;
import com.google.protobuf.InvalidProtocolBufferException;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class IntermediateServiceImpl implements IntermediateService {

    private final WebSocketUserSessionManagement webSocketUserSessionManagement;
    private final BookingServiceGrpc.BookingServiceStub bookingServiceStub;
    private final BookingServiceGrpc.BookingServiceBlockingStub bookingServiceBlockingStub;

    @Override
    public void handleBookingRequest(SaigonParkingMessage.@NotNull Builder message, @NotNull WebSocketSession webSocketSession) throws IOException {
        BookingRequestContent bookingRequestContent = BookingRequestContent.parseFrom(message.getContent());
        long newBookingId = bookingServiceBlockingStub.createBooking(CreateBookingRequest.newBuilder()
                .setParkingLotId(bookingRequestContent.getParkingLotId())
                .setCustomerId(message.getSenderId())
                .setLicensePlate(bookingRequestContent.getCustomerLicense())
                .build())
                .getValue();

        BookingProcessingContent bookingProcessingContent = BookingProcessingContent.newBuilder()
                .setParkingLotId(bookingRequestContent.getParkingLotId())
                .setBookingId(newBookingId)
                .build();

        SaigonParkingMessage bookingProcessingMessage = SaigonParkingMessage.newBuilder()
                .setSenderId(0)
                .setReceiverId(message.getSenderId())
                .setClassification(SYSTEM_MESSAGE)
                .setType(BOOKING_PROCESSING)
                .setContent(bookingProcessingContent.toByteString())
                .build();

        webSocketSession.sendMessage(new BinaryMessage(bookingProcessingMessage.toByteArray()));
    }


    @Override
    public void handleBookingCancellation(SaigonParkingMessage.@NotNull Builder message, @NotNull WebSocketSession webSocketSession) throws InvalidProtocolBufferException {
        BookingCancellationContent bookingCancellationContent = BookingCancellationContent.parseFrom(message.getContent());
        bookingServiceStub.updateBookingStatus(UpdateBookingStatusRequest.newBuilder()
                .setBookingId(bookingCancellationContent.getBookingId())
                .setStatus(BookingStatus.CANCELLED)
                .setNote(bookingCancellationContent.getReason())
                .setTimestamp(message.getTimestamp())
                .build(), new StreamObserver<Empty>() {
            @Override
            public void onNext(Empty empty) {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {

            }
        });
    }


    @Override
    public void handleBookingAcceptance(SaigonParkingMessage.@NotNull Builder message, @NotNull WebSocketSession webSocketSession) throws InvalidProtocolBufferException {
        BookingAcceptanceContent bookingAcceptanceContent = BookingAcceptanceContent.parseFrom(message.getContent());
        bookingServiceStub.updateBookingStatus(UpdateBookingStatusRequest.newBuilder()
                .setBookingId(bookingAcceptanceContent.getBookingId())
                .setStatus(BookingStatus.ACCEPTED)
                .setTimestamp(message.getTimestamp())
                .build(), new StreamObserver<Empty>() {
            @Override
            public void onNext(Empty empty) {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {

            }
        });
    }


    @Override
    public void handleBookingReject(SaigonParkingMessage.@NotNull Builder message, @NotNull WebSocketSession webSocketSession) throws InvalidProtocolBufferException {
        BookingRejectContent bookingRejectContent = BookingRejectContent.parseFrom(message.getContent());
        bookingServiceStub.updateBookingStatus(UpdateBookingStatusRequest.newBuilder()
                .setBookingId(bookingRejectContent.getBookingId())
                .setStatus(BookingStatus.REJECTED)
                .setNote(bookingRejectContent.getReason())
                .setTimestamp(message.getTimestamp())
                .build(), new StreamObserver<Empty>() {
            @Override
            public void onNext(Empty empty) {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {

            }
        });
    }


    @Override
    public void handleBookingFinish(SaigonParkingMessage.@NotNull Builder message, @NotNull WebSocketSession webSocketSession) throws InvalidProtocolBufferException {
        BookingFinishContent bookingFinishContent = BookingFinishContent.parseFrom(message.getContent());
        bookingServiceStub.updateBookingStatus(UpdateBookingStatusRequest.newBuilder()
                .setBookingId(bookingFinishContent.getBookingId())
                .setStatus(BookingStatus.FINISHED)
                .setTimestamp(message.getTimestamp())
                .build(), new StreamObserver<Empty>() {
            @Override
            public void onNext(Empty empty) {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {

            }
        });
    }
}