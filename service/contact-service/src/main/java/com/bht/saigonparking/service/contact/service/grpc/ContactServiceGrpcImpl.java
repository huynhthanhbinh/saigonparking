package com.bht.saigonparking.service.contact.service.grpc;

import org.apache.logging.log4j.Level;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import com.bht.saigonparking.api.grpc.contact.ContactServiceGrpc;
import com.bht.saigonparking.common.util.LoggingUtil;
import com.bht.saigonparking.service.contact.service.ConnectivityService;
import com.google.protobuf.BoolValue;
import com.google.protobuf.Int64Value;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@GRpcService
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class ContactServiceGrpcImpl extends ContactServiceGrpc.ContactServiceImplBase {

    private final ConnectivityService connectivityService;


    @Override
    public void checkUserOnlineByUserId(Int64Value request, StreamObserver<BoolValue> responseObserver) {
        try {
            boolean isOnline = connectivityService.isUserOnline(request.getValue());

            responseObserver.onNext(BoolValue.of(isOnline));
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("checkUserOnlineByUserId(%d): %b", request.getValue(), isOnline));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("checkUserOnlineByUserId(%d)", request.getValue()));
        }
    }

    @Override
    public void checkParkingLotOnlineByParkingLotId(Int64Value request, StreamObserver<BoolValue> responseObserver) {
        try {
            boolean isOnline = connectivityService.isParkingLotOnline(request.getValue());

            responseObserver.onNext(BoolValue.of(isOnline));
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("checkParkingLotOnlineByParkingLotId(%d): %b", request.getValue(), isOnline));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("checkParkingLotOnlineByParkingLotId(%d)", request.getValue()));
        }
    }
}