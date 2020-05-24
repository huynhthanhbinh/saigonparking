package com.bht.saigonparking.service.user.repository.core;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.service.user.entity.UserEntity;
import com.bht.saigonparking.service.user.repository.custom.UserRepositoryCustom;

/**
 *
 * @author bht
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, UserRepositoryCustom {

    /**
     *
     * self-implement getByUsername method
     * in order to prevent N+1 problem
     */
    @Query("SELECT U " +
            "FROM UserEntity U " +
            "JOIN FETCH U.userRoleEntity UR " +
            "WHERE U.username = ?1")
    UserEntity getByUsername(@NotEmpty String username);
}