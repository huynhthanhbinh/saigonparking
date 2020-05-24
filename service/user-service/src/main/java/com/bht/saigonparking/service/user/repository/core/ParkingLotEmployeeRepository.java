package com.bht.saigonparking.service.user.repository.core;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.service.user.entity.ParkingLotEmployeeEntity;

/**
 *
 * @author bht
 */
@Repository
public interface ParkingLotEmployeeRepository extends JpaRepository<ParkingLotEmployeeEntity, Long> {

    /**
     *
     * self-implement getByUsername method
     * in order to prevent N+1 problem
     */
    @Query("SELECT PLE " +
            "FROM ParkingLotEmployeeEntity PLE " +
            "JOIN FETCH PLE.userRoleEntity UR " +
            "WHERE PLE.username = ?1")
    ParkingLotEmployeeEntity getByUsername(@NotEmpty String username);
}