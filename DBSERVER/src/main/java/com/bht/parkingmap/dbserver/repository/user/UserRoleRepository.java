package com.bht.parkingmap.dbserver.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bht.parkingmap.dbserver.entity.user.UserRoleEntity;

/**
 *
 * @author bht
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
}