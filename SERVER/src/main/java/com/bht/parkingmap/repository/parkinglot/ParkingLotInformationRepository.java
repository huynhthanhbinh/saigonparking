package com.bht.parkingmap.repository.parkinglot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bht.parkingmap.entity.parkinglot.ParkingLotInformationEntity;

/**
 *
 * @author bht
 */
@Repository
public interface ParkingLotInformationRepository extends JpaRepository<ParkingLotInformationEntity, Long> {
}