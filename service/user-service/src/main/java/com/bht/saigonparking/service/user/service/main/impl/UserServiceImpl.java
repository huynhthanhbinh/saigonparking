package com.bht.saigonparking.service.user.service.main.impl;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bht.saigonparking.service.user.entity.CustomerEntity;
import com.bht.saigonparking.service.user.entity.ParkingLotEmployeeEntity;
import com.bht.saigonparking.service.user.entity.UserEntity;
import com.bht.saigonparking.service.user.repository.core.CustomerRepository;
import com.bht.saigonparking.service.user.repository.core.ParkingLotEmployeeRepository;
import com.bht.saigonparking.service.user.repository.core.UserRepository;
import com.bht.saigonparking.service.user.service.main.UserService;
import com.bht.saigonparking.service.user.util.ExceptionUtil;

import javassist.NotFoundException;
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
    public UserEntity getUserById(@NotNull Long id) throws NotFoundException {
        return userRepository.findById(id)
                .orElseThrow(ExceptionUtil::throwEntityNotFoundException);
    }

    @Override
    public UserEntity getUserByUsername(@NotNull String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public CustomerEntity getCustomerById(@NotNull Long id) throws NotFoundException {
        return customerRepository.findById(id)
                .orElseThrow(ExceptionUtil::throwEntityNotFoundException);
    }

    @Override
    public ParkingLotEmployeeEntity getParkingLotEmployeeById(@NotNull Long id) throws NotFoundException {
        return parkingLotEmployeeRepository.findById(id)
                .orElseThrow(ExceptionUtil::throwEntityNotFoundException);
    }

    @Override
    public void updateUserLastSignIn(@NotNull Long id) throws NotFoundException {
        UserEntity userEntity = getUserById(id);
        userEntity.setLastSignIn(new Timestamp(System.currentTimeMillis()));
        userRepository.saveAndFlush(userEntity);
    }
}