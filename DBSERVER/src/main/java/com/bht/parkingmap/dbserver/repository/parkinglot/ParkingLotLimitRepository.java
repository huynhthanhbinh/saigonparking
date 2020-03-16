package com.bht.parkingmap.dbserver.repository.parkinglot;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bht.parkingmap.dbserver.entity.parkinglot.ParkingLotLimitEntity;

/**
 *
 * @author bht
 */
@Repository
public interface ParkingLotLimitRepository extends JpaRepository<ParkingLotLimitEntity, Long> {

    /**
     *
     * self-implement getById method
     * in order to prevent N+1 problem
     */
    @Query("SELECT PLL " +
            "FROM ParkingLotLimitEntity PLL " +
            "JOIN FETCH PLL.parkingLotEntity PL " +
            "JOIN FETCH PL.parkingLotTypeEntity PLT " +
            "JOIN FETCH PL.parkingLotEmployeeEntity PLE " +
            "JOIN FETCH PL.parkingLotInformationEntity PLI " +
            "JOIN FETCH PLE.userRoleEntity USR " +
            "WHERE PLI.id = ?1")
    ParkingLotLimitEntity getById(@NotNull Long id);
}