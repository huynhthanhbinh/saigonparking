package com.bht.parkingmap.appserver.repository.parkinglot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bht.parkingmap.appserver.entity.parkinglot.ParkingLotSuggestionEntity;

/**
 *
 * @author bht
 */
@Repository
public interface ParkingLotSuggestionRepository extends JpaRepository<ParkingLotSuggestionEntity, Long> {
}