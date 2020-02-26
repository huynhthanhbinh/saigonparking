package com.bht.parkingmap.repository.user;

import javax.persistence.LockModeType;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bht.parkingmap.entity.user.UserEntity;

/**
 *
 * @author bht
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("UPDATE UserEntity user " +
            "SET user.lastSignIn = FUNCTION('CONVERT', TIME, FUNCTION('CURRENT_TIME'))" +
            "WHERE user.id = ?1")
    void updateLastSignIn(@NotNull Long userId);

    UserEntity getUserByUsername(@NotNull String username);
}