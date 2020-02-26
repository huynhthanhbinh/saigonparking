package com.bht.parkingmap.service.impl;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bht.parkingmap.api.proto.user.LoginResponseType;
import com.bht.parkingmap.entity.user.CustomerEntity;
import com.bht.parkingmap.entity.user.ParkingLotEmployeeEntity;
import com.bht.parkingmap.entity.user.UserEntity;
import com.bht.parkingmap.repository.user.CustomerRepository;
import com.bht.parkingmap.repository.user.ParkingLotEmployeeRepository;
import com.bht.parkingmap.repository.user.UserRepository;
import com.bht.parkingmap.service.UserService;

/**
 *
 * @author bht
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final ParkingLotEmployeeRepository parkingLotEmployeeRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           CustomerRepository customerRepository,
                           ParkingLotEmployeeRepository parkingLotEmployeeRepository) {

        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.parkingLotEmployeeRepository = parkingLotEmployeeRepository;
    }

    private void updateLastSignIn(@NotNull Long userId) {
        userRepository.updateLastSignIn(userId);
    }

    @Override
    public LoginResponseType validateLogin(@NotNull String username, @NotNull String password, @NotNull Short userRoleId) {
        UserEntity userEntity = userRepository.getUserByUsername(username);
        if (userEntity != null) {
            if (userRoleId.equals(userEntity.getUserRoleEntity().getId())) {
                if (Boolean.TRUE.equals(userEntity.getIsActivated())) {
                    if (password.equals(userEntity.getPassword())) {
                        updateLastSignIn(userEntity.getId());
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