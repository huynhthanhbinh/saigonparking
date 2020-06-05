package com.bht.saigonparking.service.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.service.auth.entity.UserTokenEntity;

/**
 *
 * @author bht
 */
@Repository
public interface UserTokenRepository extends JpaRepository<UserTokenEntity, Long> {
}