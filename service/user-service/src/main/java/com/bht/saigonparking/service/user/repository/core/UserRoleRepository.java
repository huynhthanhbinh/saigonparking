package com.bht.saigonparking.service.user.repository.core;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.service.user.entity.UserRoleEntity;

/**
 *
 * @author bht
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    Optional<UserRoleEntity> findByRole(@NotEmpty String role);
}