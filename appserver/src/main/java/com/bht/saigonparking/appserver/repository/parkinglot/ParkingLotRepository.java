package com.bht.saigonparking.appserver.repository.parkinglot;

import java.util.List;

import javax.persistence.Tuple;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.appserver.entity.parkinglot.ParkingLotEntity;
import com.bht.saigonparking.appserver.repository.custom.ParkingLotRepositoryCustom;

/**
 *
 * @author bht
 */
@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLotEntity, Long>, ParkingLotRepositoryCustom {

    /**
     *
     * self-implement getById method
     * in order to prevent N+1 problem
     */
    @Query("SELECT PL " +
            "FROM ParkingLotEntity PL " +
            "JOIN FETCH PL.parkingLotTypeEntity PLT " +
            "JOIN FETCH PL.parkingLotLimitEntity PLL " +
            "JOIN FETCH PL.parkingLotInformationEntity PLI " +
            "JOIN FETCH PL.parkingLotEmployeeEntity PLE " +
            "JOIN FETCH PLE.userRoleEntity USR " +
            "WHERE PL.id = ?1")
    ParkingLotEntity getById(@NotNull Long id);


    /**
     *
     * get all parking lot entity
     * which has not been referenced
     * by any of parking lot employee
     */
    @Query("SELECT PL " +
            "FROM ParkingLotEntity PL " +
            "JOIN FETCH PL.parkingLotTypeEntity PLT " +
            "JOIN FETCH PL.parkingLotLimitEntity PLL " +
            "JOIN FETCH PL.parkingLotInformationEntity PLI " +
            "LEFT JOIN FETCH PL.parkingLotEmployeeEntity PLE " +
            "WHERE PLE.parkingLotEntity.id IS NULL ")
    List<ParkingLotEntity> getAllIndependent();


    @Query("SELECT FUNCTION('dbo.CHECK_AVAILABILITY', P.id) " +
            "FROM ParkingLotEntity P " +
            "WHERE P.id = ?1")
    Boolean checkAvailability(@NotNull Long parkingLotId);


    @Query("SELECT P.id " +
            "FROM ParkingLotEntity P " +
            "WHERE P.id IN ?1 " +
            "AND (P.isAvailable = false OR " +
            "FUNCTION('CONVERT', TIME, FUNCTION('CURRENT_TIME')) NOT BETWEEN P.openingHour AND P.closingHour)")
    List<Long> checkUnavailability(@NotEmpty List<Long> parkingLotIdList);


    @SuppressWarnings({"SpringDataRepositoryMethodReturnTypeInspection", "SqlResolve"})
    @Query(value = "SELECT P.ID, P.PARKING_LOT_TYPE_ID, P.LATITUDE, P.LONGITUDE, PLL.AVAILABILITY, PLL.CAPACITY " +
            "FROM PARKING_LOT P " +
            "INNER JOIN (SELECT ID, CAPACITY, AVAILABILITY FROM PARKING_LOT_LIMIT) AS PLL ON PLL.ID = P.ID " +
            "AND 1 = dbo.IS_VALUE_IN_BOUND(P.LATITUDE, ?1, dbo.CALCULATE_DELTA_LAT_IN_DEGREE(?1, ?2, ?3)) " +
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


    @SuppressWarnings({"SpringDataRepositoryMethodReturnTypeInspection", "SqlResolve"})
    @Query(value = "SELECT P.ID, PLI.NAME, P.PARKING_LOT_TYPE_ID, P.LATITUDE, P.LONGITUDE, PLL.AVAILABILITY, PLL.CAPACITY " +
            "FROM PARKING_LOT P " +
            "INNER JOIN (SELECT ID, NAME FROM PARKING_LOT_INFORMATION) AS PLI ON PLI.ID = P.ID " +
            "INNER JOIN (SELECT ID, CAPACITY, AVAILABILITY FROM PARKING_LOT_LIMIT) AS PLL ON PLL.ID = P.ID " +
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