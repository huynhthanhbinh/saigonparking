package com.bht.saigonparking.service.user.service.main;

import java.util.List;

import javax.validation.constraints.Max;
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

    Long countAll();

    UserEntity getUserById(@NotNull Long id);

    UserEntity getUserByUsername(@NotEmpty String username);

    CustomerEntity getCustomerById(@NotNull Long id);

    CustomerEntity getCustomerByUsername(@NotEmpty String username);

    ParkingLotEmployeeEntity getParkingLotEmployeeById(@NotNull Long id);

    ParkingLotEmployeeEntity getParkingLotEmployeeByUsername(@NotEmpty String username);

    List<UserEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber);

    Long createCustomer(@NotNull CustomerEntity customerEntity);

    void updateCustomer(@NotNull CustomerEntity customerEntity);

    void updateUserLastSignIn(@NotNull Long id, @NotNull Long timeInMillis);

    void activateUserWithId(@NotNull Long id);

    void deactivateUserWithId(@NotNull Long id);

    void updateUserPassword(@NotNull UserEntity userEntity, @NotEmpty String newPassword);

    void deleteParkingLotEmployeeByParkingLotId(@NotNull Long parkingLotId);
}