package com.bht.saigonparking.service.user.mapper;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.api.grpc.user.Customer;
import com.bht.saigonparking.api.grpc.user.User;
import com.bht.saigonparking.api.grpc.user.UserRole;
import com.bht.saigonparking.service.user.entity.CustomerEntity;
import com.bht.saigonparking.service.user.repository.core.CustomerRepository;

import lombok.AllArgsConstructor;

/**
 *
 * @author bht
 */
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class UserMapperExtImpl implements UserMapperExt {

    private final EnumMapper enumMapper;
    private final CustomerRepository customerRepository;

    @Override
    public CustomerEntity toCustomerEntity(@NotNull Customer customer, boolean isAboutToCreate) {

        User userInfo = customer.getUserInfo();
        CustomerEntity customerEntity = !isAboutToCreate
                ? customerRepository.getByUsername(userInfo.getUsername())
                : new CustomerEntity();

        customerEntity.setUserRoleEntity(enumMapper.toUserRoleEntity(UserRole.CUSTOMER));
        customerEntity.setUsername(userInfo.getUsername());
        customerEntity.setPassword(userInfo.getPassword());
        customerEntity.setEmail(userInfo.getEmail());
        customerEntity.setFirstName(customer.getFirstName());
        customerEntity.setLastName(customer.getLastName());
        customerEntity.setPhone(customer.getPhone());

        /* Optimistic version control - prevent loss update */
        customerEntity.setVersion(userInfo.getVersion());

        return customerEntity;
    }
}