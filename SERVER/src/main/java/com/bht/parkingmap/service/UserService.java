package com.bht.parkingmap.service;

import javax.validation.constraints.NotNull;

import com.bht.parkingmap.api.proto.user.LoginResponseType;
import com.bht.parkingmap.entity.user.CustomerEntity;
import com.bht.parkingmap.entity.user.ParkingLotEmployeeEntity;
import com.bht.parkingmap.entity.user.UserEntity;

/**
 *
 * @author bht
 */
public interface UserService {

    LoginResponseType validateLogin(@NotNull String username,
                                    @NotNull String password,
                                    @NotNull Short userRoleId);

    UserEntity getUserById(@NotNull Long id);

    CustomerEntity getCustomerById(@NotNull Long id);

    ParkingLotEmployeeEntity getParkingLotEmployeeById(@NotNull Long id);
}