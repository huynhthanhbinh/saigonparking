package com.bht.saigonparking.service.user.service.main;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.bht.saigonparking.service.user.entity.CustomerEntity;
import com.bht.saigonparking.service.user.entity.ParkingLotEmployeeEntity;
import com.bht.saigonparking.service.user.entity.UserEntity;

/**
 *
 * @author bht
 */
public interface UserService {

    UserEntity getUserById(@NotNull Long id);

    UserEntity getUserByUsername(@NotEmpty String username);

    CustomerEntity getCustomerById(@NotNull Long id);

    CustomerEntity getCustomerByUsername(@NotEmpty String username);

    ParkingLotEmployeeEntity getParkingLotEmployeeById(@NotNull Long id);

    ParkingLotEmployeeEntity getParkingLotEmployeeByUsername(@NotEmpty String username);

    void updateUserLastSignIn(@NotNull Long id);

    void activateUserWithId(@NotNull Long id);

    void updateUserPassword(@NotNull Long userId, @NotEmpty String username, @NotEmpty String password);
}