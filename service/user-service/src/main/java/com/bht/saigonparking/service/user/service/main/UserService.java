package com.bht.saigonparking.service.user.service.main;

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

    UserEntity getUserByUsername(@NotNull String username);

    CustomerEntity getCustomerById(@NotNull Long id);

    ParkingLotEmployeeEntity getParkingLotEmployeeById(@NotNull Long id);

    void updateUserLastSignIn(@NotNull Long id);
}