package com.bht.saigonparking.service.user.service.grpc;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import com.bht.saigonparking.api.grpc.user.CountAllUserRequest;
import com.bht.saigonparking.api.grpc.user.Customer;
import com.bht.saigonparking.api.grpc.user.GetAllUserRequest;
import com.bht.saigonparking.api.grpc.user.GetAllUserResponse;
import com.bht.saigonparking.api.grpc.user.ParkingLotEmployee;
import com.bht.saigonparking.api.grpc.user.UpdatePasswordRequest;
import com.bht.saigonparking.api.grpc.user.User;
import com.bht.saigonparking.api.grpc.user.UserRole;
import com.bht.saigonparking.api.grpc.user.UserServiceGrpc.UserServiceImplBase;
import com.bht.saigonparking.common.exception.PermissionDeniedException;
import com.bht.saigonparking.common.exception.UsernameNotMatchException;
import com.bht.saigonparking.common.interceptor.SaigonParkingServerInterceptor;
import com.bht.saigonparking.common.util.LoggingUtil;
import com.bht.saigonparking.service.user.entity.CustomerEntity;
import com.bht.saigonparking.service.user.entity.ParkingLotEmployeeEntity;
import com.bht.saigonparking.service.user.entity.UserEntity;
import com.bht.saigonparking.service.user.mapper.EnumMapper;
import com.bht.saigonparking.service.user.mapper.UserMapper;
import com.bht.saigonparking.service.user.mapper.UserMapperExt;
import com.bht.saigonparking.service.user.service.main.UserService;
import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;

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
    private final UserMapperExt userMapperExt;
    private final EnumMapper enumMapper;
    private final SaigonParkingServerInterceptor serverInterceptor;

    @Override
    public void countAllUser(CountAllUserRequest request, StreamObserver<Int64Value> responseObserver) {
        try {
            if (!serverInterceptor.getRoleContext().get().equals("ADMIN")) {
                throw new PermissionDeniedException();
            }

            Long count;

            if (request.getUserRole().equals(UserRole.ALL)) {
                count = userService.countAll(request.getKeyword(), request.getInactivatedOnly());

            } else {
                count = userService.countAll(request.getKeyword(), request.getInactivatedOnly(), enumMapper.toUserRoleEntity(request.getUserRole()));
            }

            responseObserver.onNext(Int64Value.of(count));
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success", "countAllUser");

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL", "countAllUser");
        }
    }

    @Override
    public void getAllUser(GetAllUserRequest request, StreamObserver<GetAllUserResponse> responseObserver) {
        try {
            if (!serverInterceptor.getRoleContext().get().equals("ADMIN")) {
                throw new PermissionDeniedException();
            }

            List<User> userList;

            if (request.getUserRole().equals(UserRole.ALL)) {
                userList = userMapper.toUserList(userService
                        .getAll(request.getNRow(), request.getPageNumber(), request.getKeyword(), request.getInactivatedOnly()));

            } else {
                userList = userMapper.toUserList(userService
                        .getAll(request.getNRow(), request.getPageNumber(), request.getKeyword(), request.getInactivatedOnly(), enumMapper.toUserRoleEntity(request.getUserRole())));
            }

            responseObserver.onNext(GetAllUserResponse.newBuilder().addAllUser(userList).build());
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getAllUser(%d, %d)", request.getNRow(), request.getPageNumber()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getAllUser(%d, %d)", request.getNRow(), request.getPageNumber()));
        }
    }

    @Override
    public void getUserById(Int64Value request, StreamObserver<User> responseObserver) {
        try {
            String userRole = serverInterceptor.getRoleContext().get();

            if (!userRole.equals("ADMIN")) {
                throw new PermissionDeniedException();
            }

            User user = userMapper.toUser(userService
                    .getUserById(request.getValue()));

            responseObserver.onNext(user);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getUserById(%d)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getUserById(%d)", request.getValue()));
        }
    }

    @Override
    public void getUserByUsername(StringValue request, StreamObserver<User> responseObserver) {
        try {
            String userRole = serverInterceptor.getRoleContext().get();

            if (!userRole.equals("ADMIN")) {
                throw new PermissionDeniedException();
            }

            User user = userMapper.toUser(userService
                    .getUserByUsername(request.getValue()));

            responseObserver.onNext(user);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getUserByUsername(%s)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getUserByUsername(%s)", request.getValue()));
        }
    }

    @Override
    public void getCustomerById(Int64Value request, StreamObserver<Customer> responseObserver) {
        try {
            String userRole = serverInterceptor.getRoleContext().get();

            if (!userRole.equals("ADMIN")) {
                throw new PermissionDeniedException();
            }

            Customer customer = userMapper.toCustomer(userService
                    .getCustomerById(request.getValue()));

            responseObserver.onNext(customer);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getCustomerById(%d)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getCustomerById(%d)", request.getValue()));
        }
    }

    @Override
    public void getCustomerByUsername(StringValue request, StreamObserver<Customer> responseObserver) {
        try {
            Long userId = serverInterceptor.getUserIdContext().get();
            CustomerEntity customerEntity = userService.getCustomerByUsername(request.getValue());

            if (!userId.equals(customerEntity.getId())) {
                throw new UsernameNotMatchException();
            }

            responseObserver.onNext(userMapper.toCustomer(customerEntity));
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getCustomerByUsername(%s)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getCustomerByUsername(%s)", request.getValue()));
        }
    }

    @Override
    public void getParkingLotEmployeeById(Int64Value request, StreamObserver<ParkingLotEmployee> responseObserver) {
        try {
            String userRole = serverInterceptor.getRoleContext().get();

            if (!userRole.equals("ADMIN")) {
                throw new PermissionDeniedException();
            }

            ParkingLotEmployee parkingLotEmployee = userMapper.toParkingLotEmployee(userService
                    .getParkingLotEmployeeById(request.getValue()));

            responseObserver.onNext(parkingLotEmployee);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getParkingLotEmployeeById(%d)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getParkingLotEmployeeById(%d)", request.getValue()));
        }
    }

    @Override
    public void getParkingLotEmployeeByUsername(StringValue request, StreamObserver<ParkingLotEmployee> responseObserver) {
        try {
            Long userId = serverInterceptor.getUserIdContext().get();
            ParkingLotEmployeeEntity parkingLotEmployeeEntity = userService.getParkingLotEmployeeByUsername(request.getValue());

            if (!userId.equals(parkingLotEmployeeEntity.getId())) {
                throw new UsernameNotMatchException();
            }

            responseObserver.onNext(userMapper.toParkingLotEmployee(parkingLotEmployeeEntity));
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getParkingLotEmployeeByUsername(%s)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getParkingLotEmployeeByUsername(%s)", request.getValue()));
        }
    }

    @Override
    public void createCustomer(Customer request, StreamObserver<Int64Value> responseObserver) {
        try {
            String userRole = serverInterceptor.getRoleContext().get();

            if (!userRole.equals("ADMIN")) {
                throw new PermissionDeniedException();
            }

            CustomerEntity customerEntity = userMapperExt.toCustomerEntity(request, true);
            Long newCustomerId = userService.createCustomer(customerEntity);

            responseObserver.onNext(Int64Value.of(newCustomerId));
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("createCustomer(%s)", request.getUserInfo().getUsername()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("createCustomer(%s)", request.getUserInfo().getUsername()));
        }
    }

    @Override
    public void updateCustomer(Customer request, StreamObserver<Empty> responseObserver) {
        try {
            Long userId = serverInterceptor.getUserIdContext().get();
            CustomerEntity customerEntity = userMapperExt.toCustomerEntity(request, false);

            if (!userId.equals(customerEntity.getId())) {
                throw new UsernameNotMatchException();
            }

            userService.updateCustomer(customerEntity);

            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("updateCustomer(%s)", request.getUserInfo().getUsername()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("updateCustomer(%s)", request.getUserInfo().getUsername()));
        }
    }

    @Override
    public void updatePassword(UpdatePasswordRequest request, StreamObserver<Empty> responseObserver) {
        try {
            Long userId = serverInterceptor.getUserIdContext().get();
            UserEntity userEntity = userService.getUserByUsername(request.getUsername());

            if (!userId.equals(userEntity.getId())) {
                throw new UsernameNotMatchException();
            }

            userService.updateUserPassword(userEntity, request.getNewPassword());

            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("updatePasswordOfUser(%s)", request.getUsername()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("updatePasswordOfUser(%s)", request.getUsername()));
        }
    }

    @Override
    public void activateUser(Int64Value request, StreamObserver<Empty> responseObserver) {
        try {
            String userRole = serverInterceptor.getRoleContext().get();

            if (!userRole.equals("ADMIN")) {
                throw new PermissionDeniedException();
            }

            userService.activateUserWithId(request.getValue());

            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("activateUserWithId(%d)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("activateUserWithId(%d)", request.getValue()));
        }
    }

    @Override
    public void deactivateUser(Int64Value request, StreamObserver<Empty> responseObserver) {
        try {
            String userRole = serverInterceptor.getRoleContext().get();

            if (!userRole.equals("ADMIN")) {
                throw new PermissionDeniedException();
            }

            userService.deactivateUserWithId(request.getValue());

            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("deactivateUserWithId(%d)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("deactivateUserWithId(%d)", request.getValue()));
        }
    }
}