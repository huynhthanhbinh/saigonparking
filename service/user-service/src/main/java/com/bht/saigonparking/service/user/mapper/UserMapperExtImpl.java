package com.bht.saigonparking.service.user.mapper;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.api.grpc.user.Customer;
import com.bht.saigonparking.api.grpc.user.User;
import com.bht.saigonparking.api.grpc.user.UserRole;
import com.bht.saigonparking.service.user.entity.CustomerEntity;
import com.bht.saigonparking.service.user.entity.UserEntity;
import com.bht.saigonparking.service.user.repository.core.CustomerRepository;
import com.bht.saigonparking.service.user.repository.core.UserRepository;

import lombok.AllArgsConstructor;

/**
 *
 * @author bht
 */
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class UserMapperExtImpl implements UserMapperExt {

    private final EnumMapper enumMapper;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    @Override
    public UserEntity toUserEntity(@NotNull User user, boolean isAboutToCreate) {

        UserEntity userEntity = !isAboutToCreate
                ? userRepository.getByUsername(user.getUsername()).orElseThrow(EntityNotFoundException::new)
                : new UserEntity();

        userEntity.setUsername(user.getUsername());
        userEntity.setUserRoleEntity(enumMapper.toUserRoleEntity(user.getRole()));
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setEmail(user.getEmail());
        userEntity.setIsActivated(user.getIsActivated());

        /* Optimistic version control - prevent loss update */
        userEntity.setVersion(!isAboutToCreate ? user.getVersion() : 1L);
        return userEntity;
    }

    @Override
    public CustomerEntity toCustomerEntity(@NotNull Customer customer, boolean isAboutToCreate) {

        User userInfo = customer.getUserInfo();
        CustomerEntity customerEntity = !isAboutToCreate
                ? customerRepository.getByUsername(userInfo.getUsername()).orElseThrow(EntityNotFoundException::new)
                : new CustomerEntity();

        customerEntity.setUsername(userInfo.getUsername());
        customerEntity.setUserRoleEntity(enumMapper.toUserRoleEntity(UserRole.CUSTOMER));
        customerEntity.setPassword(isAboutToCreate ? passwordEncoder.encode(userInfo.getPassword()) : customerEntity.getPassword());
        customerEntity.setEmail(userInfo.getEmail());
        customerEntity.setFirstName(customer.getFirstName());
        customerEntity.setLastName(customer.getLastName());
        customerEntity.setPhone(customer.getPhone());

        /* Optimistic version control - prevent loss update */
        customerEntity.setVersion(!isAboutToCreate ? userInfo.getVersion() : 1L);
        return customerEntity;
    }
}