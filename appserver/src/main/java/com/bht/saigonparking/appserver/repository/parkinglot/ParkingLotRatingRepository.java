package com.bht.saigonparking.appserver.repository.parkinglot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.appserver.entity.parkinglot.ParkingLotRatingEntity;

/**
 *
 * @author bht
 */
@Repository
public interface ParkingLotRatingRepository extends JpaRepository<ParkingLotRatingEntity, Long> {
}