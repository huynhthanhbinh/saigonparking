package com.bht.saigonparking.service.booking.service.grpc;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;

import com.bht.saigonparking.api.grpc.booking.Booking;
import com.bht.saigonparking.api.grpc.booking.BookingDetail;
import com.bht.saigonparking.api.grpc.booking.BookingList;
import com.bht.saigonparking.api.grpc.booking.BookingRating;
import com.bht.saigonparking.api.grpc.booking.BookingServiceGrpc;
import com.bht.saigonparking.api.grpc.booking.BookingStatus;
import com.bht.saigonparking.api.grpc.booking.CountAllBookingGroupByStatusResponse;
import com.bht.saigonparking.api.grpc.booking.CountAllBookingOfParkingLotRequest;
import com.bht.saigonparking.api.grpc.booking.CountAllBookingRequest;
import com.bht.saigonparking.api.grpc.booking.CountAllRatingsOfParkingLotRequest;
import com.bht.saigonparking.api.grpc.booking.CreateBookingRatingRequest;
import com.bht.saigonparking.api.grpc.booking.CreateBookingRequest;
import com.bht.saigonparking.api.grpc.booking.CreateBookingResponse;
import com.bht.saigonparking.api.grpc.booking.DeleteBookingRatingRequest;
import com.bht.saigonparking.api.grpc.booking.FinishBookingRequest;
import com.bht.saigonparking.api.grpc.booking.FinishBookingResponse;
import com.bht.saigonparking.api.grpc.booking.GenerateBookingQrCodeRequest;
import com.bht.saigonparking.api.grpc.booking.GenerateBookingQrCodeResponse;
import com.bht.saigonparking.api.grpc.booking.GetAllBookingOfCustomerRequest;
import com.bht.saigonparking.api.grpc.booking.GetAllBookingOfParkingLotRequest;
import com.bht.saigonparking.api.grpc.booking.GetAllBookingRequest;
import com.bht.saigonparking.api.grpc.booking.GetAllRatingsOfParkingLotRequest;
import com.bht.saigonparking.api.grpc.booking.GetAllRatingsOfParkingLotResponse;
import com.bht.saigonparking.api.grpc.booking.ParkingLotBookingAndRatingStatistic;
import com.bht.saigonparking.api.grpc.booking.ParkingLotRatingCountGroupByRating;
import com.bht.saigonparking.api.grpc.booking.UpdateBookingRatingRequest;
import com.bht.saigonparking.api.grpc.booking.UpdateBookingStatusRequest;
import com.bht.saigonparking.common.exception.CustomerHasOnGoingBookingException;
import com.bht.saigonparking.common.interceptor.SaigonParkingServerInterceptor;
import com.bht.saigonparking.common.util.ImageUtil;
import com.bht.saigonparking.common.util.LoggingUtil;
import com.bht.saigonparking.service.booking.entity.BookingEntity;
import com.bht.saigonparking.service.booking.mapper.BookingMapper;
import com.bht.saigonparking.service.booking.mapper.CustomizedMapper;
import com.bht.saigonparking.service.booking.mapper.EnumMapper;
import com.bht.saigonparking.service.booking.service.main.BookingService;
import com.bht.saigonparking.service.booking.service.main.QrCodeService;
import com.google.protobuf.BoolValue;
import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;

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
    private final EnumMapper enumMapper;
    private final BookingMapper bookingMapper;
    private final CustomizedMapper customizedMapper;
    private final BookingService bookingService;
    private final QrCodeService qrCodeService;

    @Override
    public void createBooking(CreateBookingRequest request, StreamObserver<CreateBookingResponse> responseObserver) {
        try {
            serverInterceptor.validateAdmin();

            if (bookingService.checkCustomerHasOnGoingBooking(request.getCustomerId())) {
                throw new CustomerHasOnGoingBookingException();
            }

            Pair<String, String> newBooking = bookingService.saveNewBooking(bookingMapper.toBookingEntity(request));

            CreateBookingResponse response = CreateBookingResponse.newBuilder()
                    .setBookingId(newBooking.getFirst())
                    .setQrCode(ImageUtil.encodeImage(qrCodeService.encodeContents(newBooking.getFirst())))
                    .setCreatedAt(newBooking.getSecond())
                    .build();

            responseObserver.onNext(response);
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
                    String.format("updateBookingStatus(%s): %s", request.getBookingId(), request.getStatus()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("updateBookingStatus(%s): %s", request.getBookingId(), request.getStatus()));
        }
    }

    @Override
    public void deleteBookingById(StringValue request, StreamObserver<Empty> responseObserver) {
        try {
            serverInterceptor.validateAdmin();

            bookingService.deleteBookingByUuid(request.getValue());

            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("deleteBookingById(%s)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("deleteBookingById(%s)", request.getValue()));
        }
    }

    @Override
    public void countAllBooking(CountAllBookingRequest request, StreamObserver<Int64Value> responseObserver) {
        try {
            serverInterceptor.validateAdmin();

            Long count = request.getStatus().equals(BookingStatus.ALL)
                    ? bookingService.countAllBooking()
                    : bookingService.countAllBooking(enumMapper.toBookingStatusEntity(request.getStatus()));

            responseObserver.onNext(Int64Value.of(count));
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("countAllBooking(%s): %d", request.getStatus(), count));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("countAllBooking(%s)", request.getStatus()));
        }
    }

    @Override
    public void countAllBookingOfCustomerByCustomerId(Int64Value request, StreamObserver<Int64Value> responseObserver) {
        try {
            serverInterceptor.validateAdmin();

            Long count = bookingService.countAllBookingOfCustomer(request.getValue());

            responseObserver.onNext(Int64Value.of(count));
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("countAllBookingOfCustomerByCustomerId(%d): %d", request.getValue(), count));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("countAllBookingOfCustomerByCustomerId(%d)", request.getValue()));
        }
    }

    @Override
    public void countAllBookingOfCustomerByAuthorizationHeader(Empty request, StreamObserver<Int64Value> responseObserver) {
        Long customerId = serverInterceptor.getUserIdContext().get();
        try {
            Long count = bookingService.countAllBookingOfCustomer(customerId);

            responseObserver.onNext(Int64Value.of(count));
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("countAllBookingOfCustomerByAuthorizationHeader(%d): %d", customerId, count));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("countAllBookingOfCustomerByAuthorizationHeader(%d)", customerId));
        }
    }

    @Override
    public void countAllBookingOfParkingLot(CountAllBookingOfParkingLotRequest request, StreamObserver<Int64Value> responseObserver) {
        try {
            Long count = (request.getStatus().equals(BookingStatus.ALL))
                    ? bookingService.countAllBookingOfParkingLot(request.getParkingLotId())
                    : bookingService.countAllBookingOfParkingLot(request.getParkingLotId(), enumMapper.toBookingStatusEntity(request.getStatus()));

            responseObserver.onNext(Int64Value.of(count));
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("countAllBookingOfParkingLot(%d, %s): %d", request.getParkingLotId(), request.getStatus(), count));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("countAllBookingOfParkingLot(%d, %s)", request.getParkingLotId(), request.getStatus()));
        }
    }

    @Override
    public void countAllOnGoingBookingOfParkingLot(Int64Value request, StreamObserver<Int64Value> responseObserver) {
        try {
            Long count = bookingService.countAllOnGoingBookingOfParkingLot(request.getValue());

            responseObserver.onNext(Int64Value.of(count));
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("countAllOnGoingBookingOfParkingLot(%d): %d", request.getValue(), count));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("countAllOnGoingBookingOfParkingLot(%d)", request.getValue()));
        }
    }

    @Override
    public void getAllBooking(GetAllBookingRequest request, StreamObserver<BookingList> responseObserver) {
        try {
            List<BookingEntity> bookingEntityList = request.getStatus().equals(BookingStatus.ALL)
                    ? bookingService.getAllBooking(request.getNRow(), request.getPageNumber())
                    : bookingService.getAllBooking(enumMapper.toBookingStatusEntity(request.getStatus()), request.getNRow(), request.getPageNumber());

            Map<BookingEntity, String> bookingMap = customizedMapper.toBookingEntityParkingLotNameMap(bookingEntityList);
            BookingList bookingList = BookingList.newBuilder().addAllBooking(bookingMapper.toBookingList(bookingMap)).build();

            responseObserver.onNext(bookingList);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getAllBooking(%s, %d, %d)", request.getStatus(), request.getNRow(), request.getPageNumber()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getAllBooking(%s, %d, %d)", request.getStatus(), request.getNRow(), request.getPageNumber()));
        }
    }

    @Override
    public void getAllBookingOfCustomer(GetAllBookingOfCustomerRequest request, StreamObserver<BookingList> responseObserver) {
        Long customerId = request.getCustomerId() != 0 ? request.getCustomerId() : serverInterceptor.getUserIdContext().get();
        try {
            List<BookingEntity> bookingEntityList = bookingService
                    .getAllBookingOfCustomer(customerId, request.getNRow(), request.getPageNumber());

            Map<BookingEntity, String> bookingMap = customizedMapper.toBookingEntityParkingLotNameMap(bookingEntityList);
            BookingList bookingList = BookingList.newBuilder().addAllBooking(bookingMapper.toBookingList(bookingMap)).build();

            responseObserver.onNext(bookingList);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getAllBookingOfCustomer(%d, %d, %d)", customerId, request.getNRow(), request.getPageNumber()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getAllBookingOfCustomer(%d, %d, %d)", customerId, request.getNRow(), request.getPageNumber()));
        }
    }

    @Override
    public void getAllBookingOfParkingLot(GetAllBookingOfParkingLotRequest request, StreamObserver<BookingList> responseObserver) {
        try {
            List<BookingEntity> bookingEntityList = request.getStatus().equals(BookingStatus.ALL)
                    ? bookingService.getAllBookingOfParkingLot(request.getParkingLotId(), request.getNRow(), request.getPageNumber())
                    : bookingService.getAllBookingOfParkingLot(request.getParkingLotId(), enumMapper.toBookingStatusEntity(request.getStatus()), request.getNRow(), request.getPageNumber());

            Map<BookingEntity, String> bookingMap = customizedMapper.toBookingEntityParkingLotNameMap(bookingEntityList);
            BookingList bookingList = BookingList.newBuilder().addAllBooking(bookingMapper.toBookingList(bookingMap)).build();

            responseObserver.onNext(bookingList);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getAllBookingOfParkingLot(%d, %s, %d, %d)",
                            request.getParkingLotId(), request.getStatus(), request.getNRow(), request.getPageNumber()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getAllBookingOfParkingLot(%d, %s, %d, %d)",
                            request.getParkingLotId(), request.getStatus(), request.getNRow(), request.getPageNumber()));
        }
    }

    @Override
    public void getAllOnGoingBookingOfParkingLot(Int64Value request, StreamObserver<BookingList> responseObserver) {
        try {
            List<BookingEntity> bookingEntityList = bookingService.getAllOnGoingBookingOfParkingLot(request.getValue());

            Map<BookingEntity, String> bookingMap = customizedMapper.toBookingEntityParkingLotNameMap(bookingEntityList);
            BookingList bookingList = BookingList.newBuilder().addAllBooking(bookingMapper.toBookingList(bookingMap)).build();

            responseObserver.onNext(bookingList);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getAllOnGoingBookingOfParkingLot(%d)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getAllOnGoingBookingOfParkingLot(%d)", request.getValue()));
        }
    }

    @Override
    public void getBookingDetailByBookingId(StringValue request, StreamObserver<BookingDetail> responseObserver) {
        try {
            BookingEntity bookingEntity = bookingService.getBookingDetailByUuid(request.getValue());

            responseObserver.onNext(bookingMapper.toBookingDetail(bookingEntity));
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getBookingDetailByBookingId(%s)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getBookingDetailByBookingId(%s)", request.getValue()));
        }
    }

    @Override
    public void generateBookingQrCode(GenerateBookingQrCodeRequest request, StreamObserver<GenerateBookingQrCodeResponse> responseObserver) {
        try {
            String bookingUuid = request.getBookingId();

            /* check if booking is exist, otherwise, throw exception */
            bookingService.getBookingByUuid(bookingUuid);

            GenerateBookingQrCodeResponse response = GenerateBookingQrCodeResponse.newBuilder()
                    .setQrCode(ImageUtil.encodeImage(qrCodeService.encodeContents(bookingUuid)))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("generateBookingQrCode(%s)", request.getBookingId()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("generateBookingQrCode(%s)", request.getBookingId()));
        }
    }

    @Override
    public void finishBooking(FinishBookingRequest request, StreamObserver<FinishBookingResponse> responseObserver) {
        try {
            serverInterceptor.validateUserRole(Arrays.asList("PARKING_LOT_EMPLOYEE", "ADMIN"));

            String bookingUuid = request.getBookingId();
            Pair<Long, Long> customerParkingLotPair = bookingService.finishBooking(bookingUuid);

            FinishBookingResponse finishBookingResponse = FinishBookingResponse.newBuilder()
                    .setBookingId(bookingUuid)
                    .setCustomerId(customerParkingLotPair.getFirst())
                    .setParkingLotId(customerParkingLotPair.getSecond())
                    .build();

            responseObserver.onNext(finishBookingResponse);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("finishBooking(%s)", request.getBookingId()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("finishBooking(%s)", request.getBookingId()));
        }
    }

    @Override
    public void countAllBookingGroupByStatus(Empty request, StreamObserver<CountAllBookingGroupByStatusResponse> responseObserver) {
        try {
            serverInterceptor.validateAdmin();

            CountAllBookingGroupByStatusResponse response = CountAllBookingGroupByStatusResponse.newBuilder()
                    .putAllStatusCount(bookingService.countAllBookingGroupByStatus())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success", "countAllBookingGroupByStatus()");

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL", "countAllBookingGroupByStatus()");
        }
    }

    @Override
    public void countAllBookingOfParkingLotGroupByStatus(Int64Value request, StreamObserver<CountAllBookingGroupByStatusResponse> responseObserver) {
        try {
            serverInterceptor.validateUserRole(Arrays.asList("PARKING_LOT_EMPLOYEE", "ADMIN"));

            CountAllBookingGroupByStatusResponse response = CountAllBookingGroupByStatusResponse.newBuilder()
                    .putAllStatusCount(bookingService.countAllBookingOfParkingLotGroupByStatus(request.getValue()))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("countAllBookingOfParkingLotGroupByStatus(%d)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("countAllBookingOfParkingLotGroupByStatus(%d)", request.getValue()));
        }
    }

    @Override
    public void checkCustomerHasOnGoingBooking(Empty request, StreamObserver<BoolValue> responseObserver) {
        Long customerId = serverInterceptor.getUserIdContext().get();
        try {
            serverInterceptor.validateUserRole("CUSTOMER");

            boolean isCustomerHasOnGoingBooking = bookingService.checkCustomerHasOnGoingBooking(customerId);

            responseObserver.onNext(BoolValue.of(isCustomerHasOnGoingBooking));
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("checkCustomerHasOnGoingBooking(%d): %b", customerId, isCustomerHasOnGoingBooking));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("checkCustomerHasOnGoingBooking(%d)", customerId));
        }
    }

    @Override
    public void getCustomerOnGoingBooking(Empty request, StreamObserver<Booking> responseObserver) {
        Long customerId = serverInterceptor.getUserIdContext().get();
        try {
            serverInterceptor.validateUserRole("CUSTOMER");
            BookingEntity onGoingBooking = bookingService.getOnGoingBookingOfCustomer(customerId);

            responseObserver.onNext(bookingMapper.toBooking(onGoingBooking));
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getCustomerOnGoingBooking(%d): %s", customerId, onGoingBooking.getUuid()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getCustomerOnGoingBooking(%d)", customerId));
        }
    }

    @Override
    public void createBookingRating(CreateBookingRatingRequest request, StreamObserver<Empty> responseObserver) {
        Long customerId = serverInterceptor.getUserIdContext().get();
        try {
            serverInterceptor.validateUserRole("CUSTOMER");
            bookingService.createBookingRating(customerId, request.getBookingId(), request.getRating(), request.getComment());

            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("createBookingRating(%s, %d, %s)",
                            request.getBookingId(), request.getRating(), request.getComment()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("createBookingRating(%s, %d, %s)",
                            request.getBookingId(), request.getRating(), request.getComment()));
        }
    }

    @Override
    public void updateBookingRating(UpdateBookingRatingRequest request, StreamObserver<Empty> responseObserver) {
        Long customerId = serverInterceptor.getUserIdContext().get();
        try {
            serverInterceptor.validateUserRole("CUSTOMER");
            bookingService.updateBookingRating(customerId, request.getBookingId(), request.getRating(), request.getComment());

            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("updateBookingRating(%s, %d, %s)",
                            request.getBookingId(), request.getRating(), request.getComment()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("updateBookingRating(%s, %d, %s)",
                            request.getBookingId(), request.getRating(), request.getComment()));
        }
    }

    @Override
    public void deleteBookingRating(DeleteBookingRatingRequest request, StreamObserver<Empty> responseObserver) {
        Long customerId = serverInterceptor.getUserIdContext().get();
        try {
            serverInterceptor.validateUserRole("CUSTOMER");
            bookingService.deleteBookingRating(customerId, request.getBookingId());

            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("deleteBookingRating(%s)", request.getBookingId()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("deleteBookingRating(%s)", request.getBookingId()));
        }
    }

    @Override
    public void countAllRatingsOfParkingLot(CountAllRatingsOfParkingLotRequest request, StreamObserver<Int64Value> responseObserver) {
        try {
            Long count = bookingService.countAllRatingsOfParkingLot(request.getParkingLotId(), request.getRating());

            responseObserver.onNext(Int64Value.of(count));
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("countAllRatingsOfParkingLot(%d, %d): %d", request.getParkingLotId(), request.getRating(), count));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("countAllRatingsOfParkingLot(%d, %d)", request.getParkingLotId(), request.getRating()));
        }
    }

    @Override
    public void getAllRatingsOfParkingLot(GetAllRatingsOfParkingLotRequest request, StreamObserver<GetAllRatingsOfParkingLotResponse> responseObserver) {
        try {
            List<BookingRating> bookingRatingList = bookingMapper
                    .toBookingRatingList(bookingService
                            .getAllRatingsOfParkingLot(request.getParkingLotId(), request.getRating(),
                                    request.getSortLastUpdatedAsc(), request.getNRow(), request.getPageNumber()));

            GetAllRatingsOfParkingLotResponse getAllRatingsOfParkingLotResponse = GetAllRatingsOfParkingLotResponse.newBuilder()
                    .addAllRating(bookingRatingList)
                    .build();

            responseObserver.onNext(getAllRatingsOfParkingLotResponse);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getAllRatingsOfParkingLot(%d, %d, %b, %d, %d)", request.getParkingLotId(), request.getRating(),
                            request.getSortLastUpdatedAsc(), request.getNRow(), request.getPageNumber()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getAllRatingsOfParkingLot(%d, %d, %b, %d, %d)", request.getParkingLotId(), request.getRating(),
                            request.getSortLastUpdatedAsc(), request.getNRow(), request.getPageNumber()));
        }
    }

    @Override
    public void getParkingLotRatingCountGroupByRating(Int64Value request, StreamObserver<ParkingLotRatingCountGroupByRating> responseObserver) {
        try {
            ParkingLotRatingCountGroupByRating ratingCountGroupByRating = ParkingLotRatingCountGroupByRating.newBuilder()
                    .putAllRatingCount(bookingService.getParkingLotRatingCountGroupByRating(request.getValue()))
                    .build();

            responseObserver.onNext(ratingCountGroupByRating);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getParkingLotRatingCountGroupByRating(%d)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getParkingLotRatingCountGroupByRating(%d)", request.getValue()));
        }
    }

    @Override
    public void getParkingLotBookingAndRatingStatistic(Int64Value request, StreamObserver<ParkingLotBookingAndRatingStatistic> responseObserver) {
        super.getParkingLotBookingAndRatingStatistic(request, responseObserver);
    }
}