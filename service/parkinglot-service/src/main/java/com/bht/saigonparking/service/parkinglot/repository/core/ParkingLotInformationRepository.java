package com.bht.saigonparking.service.parkinglot.repository.core;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.service.parkinglot.entity.ParkingLotInformationEntity;
import com.bht.saigonparking.service.parkinglot.repository.custom.ParkingLotInformationRepositoryCustom;

/**
 *
 * @author bht
 */
@Repository
public interface ParkingLotInformationRepository extends JpaRepository<ParkingLotInformationEntity, Long>, ParkingLotInformationRepositoryCustom {

    /**
     *
     * self-implement getById method
     * in order to prevent N+1 problem
     */
    @Query("SELECT PLI " +
            "FROM ParkingLotInformationEntity PLI " +
            "JOIN FETCH PLI.parkingLotEntity PL " +
            "JOIN FETCH PL.parkingLotTypeEntity PLT " +
            "JOIN FETCH PL.parkingLotLimitEntity PLL " +
            "WHERE PLI.id = ?1")
    ParkingLotInformationEntity getById(@NotNull Long id);
}