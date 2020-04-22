package com.bht.saigonparking.appserver.repository.user;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.appserver.entity.user.UserEntity;
import com.bht.saigonparking.appserver.repository.custom.UserRepositoryCustom;

/**
 *
 * @author bht
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, UserRepositoryCustom {

    /**
     *
     * self-implement getById method
     * in order to prevent N+1 problem
     */
    @Query("SELECT U " +
            "FROM UserEntity U " +
            "JOIN FETCH U.userRoleEntity UR " +
            "WHERE U.id = ?1")
    UserEntity getById(@NotNull Long id);

    /**
     *
     * self-implement getByUsername method
     * in order to prevent N+1 problem
     */
    @Query("SELECT U " +
            "FROM UserEntity U " +
            "JOIN FETCH U.userRoleEntity UR " +
            "WHERE U.username = ?1")
    UserEntity getByUsername(@NotNull String username);
}