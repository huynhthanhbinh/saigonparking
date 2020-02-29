package com.bht.parkingmap.dbserver.service;

import javax.validation.constraints.NotNull;

import com.bht.parkingmap.api.proto.user.LoginResponseType;
import com.bht.parkingmap.dbserver.entity.user.CustomerEntity;
import com.bht.parkingmap.dbserver.entity.user.ParkingLotEmployeeEntity;
import com.bht.parkingmap.dbserver.entity.user.UserEntity;

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