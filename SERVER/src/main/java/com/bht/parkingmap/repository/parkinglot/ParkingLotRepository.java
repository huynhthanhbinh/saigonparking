package com.bht.parkingmap.repository.parkinglot;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bht.parkingmap.entity.parkinglot.ParkingLotEntity;

/**
 *
 * @author bht
 */
@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLotEntity, Long> {

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


    @SuppressWarnings("SqlResolve")
    @Query(value = "SELECT TOP 10 " +
            "P.ID, PLI.NAME, P.PARKING_LOT_TYPE_ID, P.LATITUDE, P.LONGITUDE, DISTANCE " +
            "FROM PARKING_LOT P " +
            "         INNER JOIN ( " +
            "    SELECT ID, NAME " +
            "    FROM PARKING_LOT_INFORMATION) AS PLI ON PLI.ID = P.ID " +
            "         INNER JOIN ( " +
            "    SELECT PL.ID, dbo.GET_DISTANCE_IN_KILOMETRE(?1, ?2, PL.LATITUDE, PL.LONGITUDE) AS DISTANCE " +
            "    FROM PARKING_LOT PL) AS PL " +
            "                    ON P.ID = PL.ID AND DISTANCE <= ?3 AND IS_AVAILABLE = 1 " +
            "                        AND CONVERT(TIME, GETDATE()) BETWEEN P.OPENING_HOUR AND P.CLOSING_HOUR " +
            "ORDER BY DISTANCE", nativeQuery = true)
    List<Tuple> getAllParkingLotCurrentlyWorkingInRegionOfRadius(
            double latitude,            // target location's latitude
            double longitude,           // target location's longitude
            int radiusInKilometre);   // radius in kilometre to scan
}