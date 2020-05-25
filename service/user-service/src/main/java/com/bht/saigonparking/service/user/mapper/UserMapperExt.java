package com.bht.saigonparking.service.user.mapper;

import javax.validation.constraints.NotNull;

import com.bht.saigonparking.api.grpc.user.Customer;
import com.bht.saigonparking.service.user.entity.CustomerEntity;

/**
 *
 * @author bht
 */
public interface UserMapperExt {

    CustomerEntity toCustomerEntity(@NotNull Customer customer, boolean isAboutToCreate);
}