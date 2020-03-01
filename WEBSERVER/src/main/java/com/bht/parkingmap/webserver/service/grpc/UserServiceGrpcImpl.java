package com.bht.parkingmap.webserver.service.grpc;

import static com.bht.parkingmap.api.proto.user.UserServiceGrpc.UserServiceImplBase;

import org.apache.log4j.Level;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import com.bht.parkingmap.api.proto.user.Customer;
import com.bht.parkingmap.api.proto.user.LoginRequest;
import com.bht.parkingmap.api.proto.user.LoginResponse;
import com.bht.parkingmap.api.proto.user.ParkingLotEmployee;
import com.bht.parkingmap.api.proto.user.User;
import com.bht.parkingmap.api.proto.user.UserServiceGrpc.UserServiceBlockingStub;
import com.bht.parkingmap.webserver.util.LoggingUtil;
import com.google.protobuf.Int64Value;

import io.grpc.stub.StreamObserver;

/**
 *
 * @author bht
 */
@GRpcService
public class UserServiceGrpcImpl extends UserServiceImplBase {

    private final UserServiceBlockingStub userServiceBlockingStub;

    @Autowired
    public UserServiceGrpcImpl(UserServiceBlockingStub userServiceBlockingStub) {
        this.userServiceBlockingStub = userServiceBlockingStub;
    }

    @Override
    public void validateLogin(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        try {
            LoginResponse loginResponse = userServiceBlockingStub.validateLogin(request);

            responseObserver.onNext(loginResponse);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("validateLogin(%s, %s, %s)",
                            request.getUsername(), request.getPassword(), request.getUserRole()));


        } catch (Exception exception) {

            responseObserver.onError(exception);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getMessage());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("validateLogin(%s, %s, %s)",
                            request.getUsername(), request.getPassword(), request.getUserRole()));
        }
    }


    @Override
    public void getUserById(Int64Value request, StreamObserver<User> responseObserver) {
        try {
            User user = userServiceBlockingStub.getUserById(request);

            responseObserver.onNext(user);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getUserById(%d)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getMessage());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getUserById(%d)", request.getValue()));
        }
    }


    @Override
    public void getCustomerById(Int64Value request, StreamObserver<Customer> responseObserver) {
        try {
            Customer customer = userServiceBlockingStub.getCustomerById(request);

            responseObserver.onNext(customer);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getCustomerById(%d)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getMessage());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getCustomerById(%d)", request.getValue()));
        }
    }


    @Override
    public void getParkingLotEmployeeById(Int64Value request, StreamObserver<ParkingLotEmployee> responseObserver) {
        try {
            ParkingLotEmployee parkingLotEmployee = userServiceBlockingStub.getParkingLotEmployeeById(request);

            responseObserver.onNext(parkingLotEmployee);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getParkingLotEmployeeById(%d)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getMessage());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getParkingLotEmployeeById(%d)", request.getValue()));
        }
    }
}