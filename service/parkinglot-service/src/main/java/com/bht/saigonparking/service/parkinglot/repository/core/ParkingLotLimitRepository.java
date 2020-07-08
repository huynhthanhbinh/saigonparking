package com.bht.saigonparking.service.parkinglot.repository.core;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.service.parkinglot.entity.ParkingLotLimitEntity;

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
            "JOIN FETCH PL.parkingLotInformationEntity PLI " +
            "WHERE PLL.id = ?1")
    ParkingLotLimitEntity getById(@NotNull Long id);


    @Modifying
    @Query("UPDATE ParkingLotLimitEntity PLL " +
            "SET PLL.availableSlot = ?1, PLL.version = PLL.version + 1 " +
            "WHERE PLL.availableSlot <> ?1 AND PLL.parkingLotEntity.id = ?2")
    void updateAvailability(@NotNull Short newAvailability, @NotNull Long parkingLotId);
}