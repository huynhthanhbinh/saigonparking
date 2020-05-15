package com.bht.saigonparking.service.user.service.main;

import javax.validation.constraints.NotNull;

import com.bht.saigonparking.service.user.entity.CustomerEntity;
import com.bht.saigonparking.service.user.entity.ParkingLotEmployeeEntity;
import com.bht.saigonparking.service.user.entity.UserEntity;

import javassist.NotFoundException;

/**
 *
 * @author bht
 */
public interface UserService {

    UserEntity getUserById(@NotNull Long id) throws NotFoundException;

    UserEntity getUserByUsername(@NotNull String username);

    CustomerEntity getCustomerById(@NotNull Long id) throws NotFoundException;

    ParkingLotEmployeeEntity getParkingLotEmployeeById(@NotNull Long id) throws NotFoundException;

    void updateUserLastSignIn(@NotNull Long id) throws NotFoundException;
}