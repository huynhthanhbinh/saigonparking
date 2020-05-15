package com.bht.saigonparking.service.user.repository.custom.impl;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.service.user.base.BaseRepositoryCustom;
import com.bht.saigonparking.service.user.entity.UserEntity;
import com.bht.saigonparking.service.user.repository.core.UserRepository;
import com.bht.saigonparking.service.user.repository.custom.UserRepositoryCustom;

import lombok.AllArgsConstructor;

/**
 *
 * @author bht
 */
@Repository
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserRepositoryCustomImpl extends BaseRepositoryCustom implements UserRepositoryCustom {

    private final UserRepository userRepository;

    @Override
    public void updateUserLastSignIn(@NotNull Long id) {
        UserEntity userEntity = userRepository.getOne(id);
        userEntity.setLastSignIn(new Timestamp(System.currentTimeMillis()));
        userRepository.save(userEntity);
    }
}