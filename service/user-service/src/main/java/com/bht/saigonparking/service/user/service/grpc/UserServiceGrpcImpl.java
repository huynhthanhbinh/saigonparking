package com.bht.saigonparking.service.user.service.grpc;

import java.util.HashSet;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import com.bht.saigonparking.api.grpc.user.CountAllUserGroupByRoleResponse;
import com.bht.saigonparking.api.grpc.user.CountAllUserRequest;
import com.bht.saigonparking.api.grpc.user.Customer;
import com.bht.saigonparking.api.grpc.user.DeleteMultiUserByIdRequest;
import com.bht.saigonparking.api.grpc.user.GetAllUserRequest;
import com.bht.saigonparking.api.grpc.user.GetAllUserResponse;
import com.bht.saigonparking.api.grpc.user.MapToUsernameMapRequest;
import com.bht.saigonparking.api.grpc.user.MapToUsernameMapResponse;
import com.bht.saigonparking.api.grpc.user.UpdatePasswordRequest;
import com.bht.saigonparking.api.grpc.user.User;
import com.bht.saigonparking.api.grpc.user.UserRole;
import com.bht.saigonparking.api.grpc.user.UserServiceGrpc.UserServiceImplBase;
import com.bht.saigonparking.common.interceptor.SaigonParkingServerInterceptor;
import com.bht.saigonparking.common.util.LoggingUtil;
import com.bht.saigonparking.service.user.entity.CustomerEntity;
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
            serverInterceptor.validateAdmin();

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
            serverInterceptor.validateAdmin();

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
            serverInterceptor.validateAdmin();

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
            serverInterceptor.validateAdmin();

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
            CustomerEntity customerEntity = userService.getCustomerByUsername(request.getValue());

            serverInterceptor.validateUser(customerEntity.getId());

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
    public void mapToUsernameMap(MapToUsernameMapRequest request, StreamObserver<MapToUsernameMapResponse> responseObserver) {
        try {
            serverInterceptor.validateAdmin();

            MapToUsernameMapResponse mapToUsernameMapResponse = MapToUsernameMapResponse.newBuilder()
                    .putAllUsername(userService.mapToUsernameMap(new HashSet<>(request.getUserIdList())))
                    .build();

            responseObserver.onNext(mapToUsernameMapResponse);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success", "mapToUsernameMap()");

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL", "mapToUsernameMap()");
        }
    }

    @Override
    public void createCustomer(Customer request, StreamObserver<Int64Value> responseObserver) {
        try {
            serverInterceptor.validateAdmin();

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
            CustomerEntity customerEntity = userMapperExt.toCustomerEntity(request, false);

            serverInterceptor.validateUser(customerEntity.getId());

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
            UserEntity userEntity = userService.getUserByUsername(request.getUsername());

            serverInterceptor.validateUser(userEntity.getId());

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
            serverInterceptor.validateAdmin();

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
            serverInterceptor.validateAdmin();

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

    @Override
    public void deleteUserById(Int64Value request, StreamObserver<Empty> responseObserver) {
        try {
            serverInterceptor.validateAdmin();

            userService.deleteUserById(request.getValue());

            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("deleteUserById(%d)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("deleteUserById(%d)", request.getValue()));
        }
    }

    @Override
    public void deleteMultiUserById(DeleteMultiUserByIdRequest request, StreamObserver<Empty> responseObserver) {
        try {
            serverInterceptor.validateAdmin();

            userService.deleteMultiUserById(new HashSet<>(request.getUserIdList()));

            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("deleteMultiUserById(%s)", request.getUserIdList()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("deleteMultiUserById(%s)", request.getUserIdList()));
        }
    }

    @Override
    public void countAllUserGroupByRole(Empty request, StreamObserver<CountAllUserGroupByRoleResponse> responseObserver) {
        super.countAllUserGroupByRole(request, responseObserver);
    }
}