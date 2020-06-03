package com.bht.saigonparking.service.user.repository.core;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.service.user.entity.CustomerEntity;

/**
 *
 * @author bht
 */
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    /**
     *
     * self-implement getByUsername method
     * in order to prevent N+1 problem
     */
    @Query("SELECT C " +
            "FROM CustomerEntity C " +
            "JOIN FETCH C.userRoleEntity UR " +
            "WHERE C.username = ?1")
    Optional<CustomerEntity> getByUsername(@NotEmpty String username);
}