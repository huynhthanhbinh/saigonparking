package com.bht.saigonparking.appserver.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.appserver.entity.user.UserRoleEntity;

/**
 *
 * @author bht
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
}