package com.bht.parkingmap.repository.parkinglot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bht.parkingmap.entity.parkinglot.ParkingLotTypeEntity;

/**
 *
 * @author bht
 */
@Repository
public interface ParkingLotTypeRepository extends JpaRepository<ParkingLotTypeEntity, Short> {
}