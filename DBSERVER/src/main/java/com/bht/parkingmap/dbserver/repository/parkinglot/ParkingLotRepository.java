package com.bht.parkingmap.dbserver.repository.parkinglot;

import java.util.List;

import javax.persistence.Tuple;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bht.parkingmap.dbserver.entity.parkinglot.ParkingLotEntity;

/**
 *
 * @author bht
 */
@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLotEntity, Long> {

    @SuppressWarnings({"SqlResolve", "SpringDataRepositoryMethodReturnTypeInspection"})
    @Query(value = "SELECT P.ID, P.PARKING_LOT_TYPE_ID, P.LATITUDE, P.LONGITUDE " +
            "FROM PARKING_LOT P " +
            "WHERE 1 = dbo.IS_VALUE_IN_BOUND(P.LATITUDE, ?1, dbo.CALCULATE_DELTA_LAT_IN_DEGREE(?1, ?2, ?3)) " +
            "AND 1 = dbo.IS_VALUE_IN_BOUND(P.LONGITUDE, ?2, dbo.CALCULATE_DELTA_LNG_IN_DEGREE(?1, ?2, ?3)) " +
            "AND 1 = P.IS_AVAILABLE " +
            "AND CONVERT(TIME, GETDATE()) BETWEEN P.OPENING_HOUR AND P.CLOSING_HOUR " +
            "ORDER BY dbo.GET_DISTANCE_IN_KILOMETRE(?1, ?2, P.LATITUDE, P.LONGITUDE) " +
            "OFFSET 0 ROWS FETCH NEXT ?4 ROWS ONLY", nativeQuery = true)
    List<Tuple> getTopParkingLotInRegionOrderByDistanceWithoutName(
            @NotNull Double lat,
            @NotNull Double lng,
            @NotNull Integer radius,
            @NotNull Integer nResult);

    @SuppressWarnings({"SqlResolve", "SpringDataRepositoryMethodReturnTypeInspection"})
    @Query(value = "SELECT P.ID, PLI.NAME, P.PARKING_LOT_TYPE_ID, P.LATITUDE, P.LONGITUDE " +
            "FROM PARKING_LOT P INNER JOIN (SELECT ID, NAME FROM PARKING_LOT_INFORMATION) AS PLI ON PLI.ID = P.ID " +
            "AND 1 = dbo.IS_VALUE_IN_BOUND(P.LATITUDE, ?1, dbo.CALCULATE_DELTA_LAT_IN_DEGREE(?1, ?2, ?3)) " +
            "AND 1 = dbo.IS_VALUE_IN_BOUND(P.LONGITUDE, ?2, dbo.CALCULATE_DELTA_LNG_IN_DEGREE(?1, ?2, ?3)) " +
            "AND 1 = P.IS_AVAILABLE " +
            "AND CONVERT(TIME, GETDATE()) BETWEEN P.OPENING_HOUR AND P.CLOSING_HOUR " +
            "ORDER BY dbo.GET_DISTANCE_IN_KILOMETRE(?1, ?2, P.LATITUDE, P.LONGITUDE) " +
            "OFFSET 0 ROWS FETCH NEXT ?4 ROWS ONLY", nativeQuery = true)
    List<Tuple> getTopParkingLotInRegionOrderByDistanceWithName(
            @NotNull Double latitude,
            @NotNull Double longitude,
            @NotNull Integer radius,
            @NotNull Integer nResult);
}