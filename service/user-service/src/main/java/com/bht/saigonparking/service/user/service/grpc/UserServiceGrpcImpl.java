package com.bht.saigonparking.service.user.service.grpc;

import org.apache.logging.log4j.Level;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import com.bht.saigonparking.api.grpc.user.Customer;
import com.bht.saigonparking.api.grpc.user.ParkingLotEmployee;
import com.bht.saigonparking.api.grpc.user.UpdatePasswordRequest;
import com.bht.saigonparking.api.grpc.user.User;
import com.bht.saigonparking.api.grpc.user.UserServiceGrpc.UserServiceImplBase;
import com.bht.saigonparking.common.interceptor.SaigonParkingServerInterceptor;
import com.bht.saigonparking.common.util.LoggingUtil;
import com.bht.saigonparking.service.user.mapper.EnumMapper;
import com.bht.saigonparking.service.user.mapper.UserMapper;
import com.bht.saigonparking.service.user.service.main.UserService;
import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
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
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class UserServiceGrpcImpl extends UserServiceImplBase {

    private final UserService userService;
    private final UserMapper userMapper;
    private final EnumMapper enumMapper;
    private final SaigonParkingServerInterceptor serverInterceptor;

    @Override
    public void getUserById(Int64Value request, StreamObserver<User> responseObserver) {
        try {
            String userRole = serverInterceptor.getRoleContext().get();

            if (!userRole.equals("ADMIN")) {
                throw new StatusRuntimeException(Status.PERMISSION_DENIED);
            }

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
    public void getUserByUsername(StringValue request, StreamObserver<User> responseObserver) {
        try {
            User user = userMapper.toUser(userService
                    .getUserByUsername(request.getValue()));

            if (user.equals(User.getDefaultInstance())) {
                throw new StatusRuntimeException(Status.UNKNOWN);
            }

            responseObserver.onNext(user);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getUserByUsername(%s)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getMessage());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getUserByUsername(%s)", request.getValue()));
        }
    }


    @Override
    public void getCustomerByUsername(StringValue request, StreamObserver<Customer> responseObserver) {
        try {
            Customer customer = userMapper.toCustomer(userService
                    .getCustomerByUsername(request.getValue()));

            if (customer.equals(Customer.getDefaultInstance())) {
                throw new StatusRuntimeException(Status.UNKNOWN);
            }

            responseObserver.onNext(customer);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getCustomerByUsername(%s)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getMessage());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getCustomerByUsername(%s)", request.getValue()));
        }
    }


    @Override
    public void getParkingLotEmployeeByUsername(StringValue request, StreamObserver<ParkingLotEmployee> responseObserver) {
        try {
            ParkingLotEmployee parkingLotEmployee = userMapper.toParkingLotEmployee(userService
                    .getParkingLotEmployeeByUsername(request.getValue()));

            if (parkingLotEmployee.equals(ParkingLotEmployee.getDefaultInstance())) {
                throw new StatusRuntimeException(Status.UNKNOWN);
            }

            responseObserver.onNext(parkingLotEmployee);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getParkingLotEmployeeByUsername(%s)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getMessage());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getParkingLotEmployeeByUsername(%s)", request.getValue()));
        }
    }


    @Override
    public void updateUserLastSignIn(Int64Value request, StreamObserver<Empty> responseObserver) {
        try {
            userService.updateUserLastSignIn(request.getValue());

            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("updateUserLastSignIn(%d)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getMessage());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("updateUserLastSignIn(%d)", request.getValue()));
        }
    }

    @Override
    public void registerCustomer(Customer request, StreamObserver<Int64Value> responseObserver) {
        super.registerCustomer(request, responseObserver);
    }

    @Override
    public void updatePassword(UpdatePasswordRequest request, StreamObserver<Empty> responseObserver) {
        try {
            Long userId = serverInterceptor.getUserIdContext().get();
            userService.updateUserPassword(userId, request.getUsername(), request.getNewPassword());

            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("updatePasswordOfUser(%s)", request.getUsername()));

        } catch (Exception exception) {

            responseObserver.onError(exception);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getMessage());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("updatePasswordOfUser(%s)", request.getUsername()));
        }
    }

    @Override
    public void activateUser(Int64Value request, StreamObserver<Empty> responseObserver) {
        try {
            String userRole = serverInterceptor.getRoleContext().get();

            if (!userRole.equals("ADMIN")) {
                throw new StatusRuntimeException(Status.PERMISSION_DENIED);
            }

            userService.activateUserWithId(request.getValue());

            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("activateUserWithId(%d)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getMessage());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("activateUserWithId(%d)", request.getValue()));
        }
    }
}