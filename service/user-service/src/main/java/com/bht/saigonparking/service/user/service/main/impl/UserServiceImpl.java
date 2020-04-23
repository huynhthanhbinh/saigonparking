package com.bht.saigonparking.service.user.service.main.impl;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bht.saigonparking.api.grpc.user.LoginResponseType;
import com.bht.saigonparking.service.user.entity.CustomerEntity;
import com.bht.saigonparking.service.user.entity.ParkingLotEmployeeEntity;
import com.bht.saigonparking.service.user.entity.UserEntity;
import com.bht.saigonparking.service.user.entity.UserRoleEntity;
import com.bht.saigonparking.service.user.repository.core.CustomerRepository;
import com.bht.saigonparking.service.user.repository.core.ParkingLotEmployeeRepository;
import com.bht.saigonparking.service.user.repository.core.UserRepository;
import com.bht.saigonparking.service.user.service.main.UserService;

import lombok.AllArgsConstructor;

/**
 *
 * this class implements all services relevant to User
 *
 * for clean code purpose,
 * using {@code @AllArgsConstructor} for Service class
 * it will {@code @Autowired} all attributes declared inside
 * hide {@code @Autowired} as much as possible in code
 * remember to mark all attributes as {@code private final}
 *
 * @author bht
 */
@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final ParkingLotEmployeeRepository parkingLotEmployeeRepository;

    @Override
    public LoginResponseType validateLogin(@NotNull String username, @NotNull String password, @NotNull UserRoleEntity userRoleEntity) {
        UserEntity userEntity = userRepository.getByUsername(username);
        if (userEntity != null) {
            if (userRoleEntity.equals(userEntity.getUserRoleEntity())) {
                if (Boolean.TRUE.equals(userEntity.getIsActivated())) {
                    if (password.equals(userEntity.getPassword())) {
                        userEntity.setLastSignIn(new Timestamp(System.currentTimeMillis()));
                        userRepository.save(userEntity);
                        return LoginResponseType.SUCCESS;
                    }
                    return LoginResponseType.INCORRECT;
                }
                return LoginResponseType.INACTIVATED;
            }
            return LoginResponseType.NON_EXIST;
        }
        return LoginResponseType.NON_EXIST;
    }

    @Override
    public UserEntity getUserById(@NotNull Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public CustomerEntity getCustomerById(@NotNull Long id) {
        return customerRepository.getOne(id);
    }

    @Override
    public ParkingLotEmployeeEntity getParkingLotEmployeeById(@NotNull Long id) {
        return parkingLotEmployeeRepository.getOne(id);
    }
}