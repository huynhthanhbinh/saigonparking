package com.bht.parkingmap.dbserver.repository.user;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bht.parkingmap.dbserver.entity.user.UserEntity;

/**
 *
 * @author bht
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity getUserByUsername(@NotNull String username);
}