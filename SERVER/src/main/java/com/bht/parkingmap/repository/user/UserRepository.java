package com.bht.parkingmap.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bht.parkingmap.entity.user.UserEntity;

/**
 *
 * @author bht
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}