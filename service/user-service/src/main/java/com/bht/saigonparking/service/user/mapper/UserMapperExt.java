package com.bht.saigonparking.service.user.mapper;

import javax.validation.constraints.NotNull;

import com.bht.saigonparking.api.grpc.user.Customer;
import com.bht.saigonparking.api.grpc.user.User;
import com.bht.saigonparking.service.user.entity.CustomerEntity;
import com.bht.saigonparking.service.user.entity.UserEntity;

/**
 *
 * @author bht
 */
public interface UserMapperExt {

    UserEntity toUserEntity(@NotNull User user, boolean isAboutToCreate);

    CustomerEntity toCustomerEntity(@NotNull Customer customer, boolean isAboutToCreate);
}