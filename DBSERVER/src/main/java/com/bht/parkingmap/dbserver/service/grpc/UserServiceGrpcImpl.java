package com.bht.parkingmap.dbserver.service.grpc;

import org.apache.logging.log4j.Level;
import org.lognet.springboot.grpc.GRpcService;

import com.bht.parkingmap.api.proto.user.Customer;
import com.bht.parkingmap.api.proto.user.LoginRequest;
import com.bht.parkingmap.api.proto.user.LoginResponse;
import com.bht.parkingmap.api.proto.user.ParkingLotEmployee;
import com.bht.parkingmap.api.proto.user.User;
import com.bht.parkingmap.api.proto.user.UserServiceGrpc.UserServiceImplBase;
import com.bht.parkingmap.dbserver.mapper.EnumMapper;
import com.bht.parkingmap.dbserver.mapper.UserMapper;
import com.bht.parkingmap.dbserver.service.UserService;
import com.bht.parkingmap.dbserver.util.LoggingUtil;
import com.google.protobuf.Int64Value;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;

/**
 *
 * this class implements all services of UserStub
 *
 * for clean code purpose,
 * using {@code @AllArgsConstructor} for Service class
 * it will {@code @Autowired} all attributes declared inside
 * hide {@code @Autowired} as much as possible in code
 * remember to mark all attributes as {@code private final}
 *
 * @author bht
 */
@GRpcService
@AllArgsConstructor
public final class UserServiceGrpcImpl extends UserServiceImplBase {

    private final UserService userService;
    private final UserMapper userMapper;
    private final EnumMapper enumMapper;

    @Override
    public void validateLogin(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        try {
            LoginResponse loginResponse = LoginResponse.newBuilder().setResponse(userService
                    .validateLogin(
                            request.getUsername(),
                            request.getPassword(),
                            enumMapper.toUserRoleEntity(request.getUserRole())))
                    .build();

            responseObserver.onNext(loginResponse);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("validateLogin(%s, %s, %s): %s",
                            request.getUsername(), request.getPassword(), request.getUserRole(), loginResponse.toString()));


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
            User user = userMapper.toUser(userService
                    .getUserById(request.getValue()));

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
            Customer customer = userMapper.toCustomer(userService
                    .getCustomerById(request.getValue()));

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
            ParkingLotEmployee parkingLotEmployee = userMapper.toParkingLotEmployee(userService
                    .getParkingLotEmployeeById(request.getValue()));

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