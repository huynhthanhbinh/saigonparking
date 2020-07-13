package com.bht.saigonparking.service.booking.service.grpc;

import org.lognet.springboot.grpc.GRpcService;

import com.bht.saigonparking.api.grpc.booking.BookingDetail;
import com.bht.saigonparking.api.grpc.booking.BookingList;
import com.bht.saigonparking.api.grpc.booking.BookingServiceGrpc;
import com.bht.saigonparking.api.grpc.booking.CreateBookingRequest;
import com.bht.saigonparking.api.grpc.booking.GetAllBookingOfParkingLotRequest;
import com.bht.saigonparking.api.grpc.booking.GetAllBookingRequest;
import com.bht.saigonparking.api.grpc.booking.UpdateBookingStatusRequest;
import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;

import io.grpc.stub.StreamObserver;

/**
 *
 * @author bht
 */
@GRpcService
public final class BookingServiceGrpcImpl extends BookingServiceGrpc.BookingServiceImplBase {

    @Override
    public void createBooking(CreateBookingRequest request, StreamObserver<Int64Value> responseObserver) {
        super.createBooking(request, responseObserver);
    }

    @Override
    public void updateBookingStatus(UpdateBookingStatusRequest request, StreamObserver<Empty> responseObserver) {
        super.updateBookingStatus(request, responseObserver);
    }

    @Override
    public void deleteBookingById(Int64Value request, StreamObserver<Empty> responseObserver) {
        super.deleteBookingById(request, responseObserver);
    }

    @Override
    public void getAllBooking(GetAllBookingRequest request, StreamObserver<BookingList> responseObserver) {
        super.getAllBooking(request, responseObserver);
    }

    @Override
    public void getAllBookingOfParkingLot(GetAllBookingOfParkingLotRequest request, StreamObserver<BookingList> responseObserver) {
        super.getAllBookingOfParkingLot(request, responseObserver);
    }

    @Override
    public void getAllOnGoingBookingOfParkingLot(Int64Value request, StreamObserver<BookingList> responseObserver) {
        super.getAllOnGoingBookingOfParkingLot(request, responseObserver);
    }

    @Override
    public void getBookingDetailById(Int64Value request, StreamObserver<BookingDetail> responseObserver) {
        super.getBookingDetailById(request, responseObserver);
    }
}