package com.bht.parkingmap.dbserver.service.impl;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bht.parkingmap.api.proto.user.LoginResponseType;
import com.bht.parkingmap.dbserver.entity.user.CustomerEntity;
import com.bht.parkingmap.dbserver.entity.user.ParkingLotEmployeeEntity;
import com.bht.parkingmap.dbserver.entity.user.UserEntity;
import com.bht.parkingmap.dbserver.entity.user.UserRoleEntity;
import com.bht.parkingmap.dbserver.repository.user.CustomerRepository;
import com.bht.parkingmap.dbserver.repository.user.ParkingLotEmployeeRepository;
import com.bht.parkingmap.dbserver.repository.user.UserRepository;
import com.bht.parkingmap.dbserver.service.UserService;

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
@AllArgsConstructor
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