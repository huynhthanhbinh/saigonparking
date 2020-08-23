package com.bht.saigonparking.service.auth.repository;

import java.util.Optional;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.service.auth.entity.UserTokenEntity;

/**
 *
 * @author bht
 */
@Repository
public interface UserTokenRepository extends JpaRepository<UserTokenEntity, Long> {

    /**
     * self-implement findByTokenId method
     * in order to prevent N+1 problem
     * using optional to catch null return
     */
    @Query("SELECT UT " +
            "FROM UserTokenEntity UT " +
            "WHERE UT.tokenId = ?1")
    Optional<UserTokenEntity> findByTokenId(@NotEmpty UUID tokenId);
}