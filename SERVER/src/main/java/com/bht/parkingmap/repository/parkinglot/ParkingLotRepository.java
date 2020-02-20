package com.bht.parkingmap.repository.parkinglot;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bht.parkingmap.entity.parkinglot.ParkingLotEntity;

/**
 *
 * @author bht
 */
@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLotEntity, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT P " +
            "FROM ParkingLotEntity P " +
            "WHERE P.latitude BETWEEN ?3 AND ?1 " +
            "AND P.longitude BETWEEN ?4 AND ?2 " +
            "AND FUNCTION('CONVERT', TIME, FUNCTION('CURRENT_TIME')) BETWEEN P.openingHour AND P.closingHour " +
            "AND P.isAvailable = TRUE")
    List<ParkingLotEntity> getAllParkingLotCurrentlyWorkingInRegion(
            double top,     // north limit
            double right,   // east limit
            double bottom,  // south limit
            double left);   // west limit


    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT P " +
            "FROM ParkingLotEntity P " +
            "WHERE FUNCTION('dbo.GET_DISTANCE_IN_KILOMETRE', ?1, ?2, P.latitude, P.longitude) <= ?3 " +
            "AND FUNCTION('CONVERT', TIME, FUNCTION('CURRENT_TIME')) BETWEEN P.openingHour AND P.closingHour " +
            "AND P.isAvailable = TRUE")
    List<ParkingLotEntity> getAllParkingLotCurrentlyWorkingInRegionOfRadius(
            double latitude,            // target location's latitude
            double longitude,           // target location's longitude
            short radiusInKilometre);   // radius in kilometre to scan
}