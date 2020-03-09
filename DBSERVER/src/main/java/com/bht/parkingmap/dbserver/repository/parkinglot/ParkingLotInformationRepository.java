package com.bht.parkingmap.dbserver.repository.parkinglot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bht.parkingmap.dbserver.entity.parkinglot.ParkingLotInformationEntity;

/**
 *
 * @author bht
 */
@Repository
public interface ParkingLotInformationRepository extends JpaRepository<ParkingLotInformationEntity, Long> {

    /**
     *
     * self-implement getById method
     * in order to prevent N+1 problem
     */
    @Query("SELECT PLI " +
            "FROM ParkingLotInformationEntity PLI " +
            "JOIN FETCH PLI.parkingLotEntity PL " +
            "JOIN FETCH PL.parkingLotTypeEntity PLT " +
            "JOIN FETCH PL.parkingLotEmployeeEntity PLE " +
            "JOIN FETCH PLE.userRoleEntity USR " +
            "WHERE PLI.id = ?1")
    ParkingLotInformationEntity getById(Long id);
}