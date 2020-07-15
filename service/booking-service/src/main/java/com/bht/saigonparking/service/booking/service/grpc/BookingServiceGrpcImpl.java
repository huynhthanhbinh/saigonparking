package com.bht.saigonparking.service.booking.service.grpc;

import org.apache.logging.log4j.Level;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import com.bht.saigonparking.api.grpc.booking.BookingDetail;
import com.bht.saigonparking.api.grpc.booking.BookingList;
import com.bht.saigonparking.api.grpc.booking.BookingServiceGrpc;
import com.bht.saigonparking.api.grpc.booking.CreateBookingRequest;
import com.bht.saigonparking.api.grpc.booking.GetAllBookingOfParkingLotRequest;
import com.bht.saigonparking.api.grpc.booking.GetAllBookingRequest;
import com.bht.saigonparking.api.grpc.booking.UpdateBookingStatusRequest;
import com.bht.saigonparking.common.interceptor.SaigonParkingServerInterceptor;
import com.bht.saigonparking.common.util.LoggingUtil;
import com.bht.saigonparking.service.booking.mapper.BookingMapper;
import com.bht.saigonparking.service.booking.service.main.BookingService;
import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@GRpcService
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class BookingServiceGrpcImpl extends BookingServiceGrpc.BookingServiceImplBase {

    private final SaigonParkingServerInterceptor serverInterceptor;
    private final BookingMapper bookingMapper;
    private final BookingService bookingService;

    @Override
    public void createBooking(CreateBookingRequest request, StreamObserver<Int64Value> responseObserver) {
        try {
            serverInterceptor.validateAdmin();

            Long newBookingId = bookingService.saveNewBooking(bookingMapper.toBookingEntity(request));

            responseObserver.onNext(Int64Value.of(newBookingId));
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("createBooking(%d, %d, %s)", request.getParkingLotId(), request.getCustomerId(), request.getLicensePlate()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("createBooking(%d, %d, %s)", request.getParkingLotId(), request.getCustomerId(), request.getLicensePlate()));
        }
    }

    @Override
    public void updateBookingStatus(UpdateBookingStatusRequest request, StreamObserver<Empty> responseObserver) {
        try {
            serverInterceptor.validateAdmin();

            bookingService.saveNewBookingHistory(bookingMapper.toBookingHistoryEntity(request), request.getBookingId());

            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("updateBookingStatus(%d): %s", request.getBookingId(), request.getStatus()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("updateBookingStatus(%d): %s", request.getBookingId(), request.getStatus()));
        }
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