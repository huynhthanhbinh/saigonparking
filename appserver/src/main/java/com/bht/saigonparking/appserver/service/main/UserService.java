package com.bht.saigonparking.appserver.service.main;

import javax.validation.constraints.NotNull;

import com.bht.saigonparking.api.grpc.user.LoginResponseType;
import com.bht.saigonparking.appserver.entity.user.CustomerEntity;
import com.bht.saigonparking.appserver.entity.user.ParkingLotEmployeeEntity;
import com.bht.saigonparking.appserver.entity.user.UserEntity;
import com.bht.saigonparking.appserver.entity.user.UserRoleEntity;

/**
 *
 * @author bht
 */
public interface UserService {

    LoginResponseType validateLogin(@NotNull String username,
                                    @NotNull String password,
                                    @NotNull UserRoleEntity userRoleEntity);

    UserEntity getUserById(@NotNull Long id);

    CustomerEntity getCustomerById(@NotNull Long id);

    ParkingLotEmployeeEntity getParkingLotEmployeeById(@NotNull Long id);
}