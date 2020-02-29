package com.bht.parkingmap.dbserver.repository.parkinglot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bht.parkingmap.dbserver.entity.parkinglot.ParkingLotUnitEntity;

/**
 *
 * @author bht
 */
@Repository
public interface ParkingLotUnitRepository extends JpaRepository<ParkingLotUnitEntity, Long> {
}