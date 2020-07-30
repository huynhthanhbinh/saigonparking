package com.bht.saigonparking.service.parkinglot.service.main;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Tuple;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.bht.saigonparking.service.parkinglot.entity.ParkingLotEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotLimitEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotTypeEntity;

/**
 *
 * @author bht
 */
public interface ParkingLotService {

    Long getParkingLotIdByParkingLotEmployeeId(@NotNull Long parkingLotEmployeeId);

    Long countAll(@NotEmpty String keyword, boolean isAvailableOnly);

    Long countAll(@NotEmpty String keyword, boolean isAvailableOnly, @NotNull ParkingLotTypeEntity parkingLotTypeEntity);

    Long countAllHasRatings();

    Long countAllHasRatings(@NotNull @Range(max = 5L) Integer lowerBound,
                            @NotNull @Range(max = 5L) Integer upperBound);

    Long countAllRatingsOfParkingLot(@NotNull Long parkingLotId);

    Long countAllRatingsOfParkingLot(@NotNull Long parkingLotId, @NotNull @Range(max = 5L) Integer rating);

    List<ParkingLotEntity> getAll(@NotNull Set<Long> parkingLotIdSet);

    List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow,
                                  @NotNull Integer pageNumber,
                                  @NotEmpty String keyword,
                                  boolean isAvailableOnly);

    List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow,
                                  @NotNull Integer pageNumber,
                                  @NotEmpty String keyword,
                                  boolean isAvailableOnly,
                                  @NotNull ParkingLotTypeEntity parkingLotTypeEntity);

    List<Tuple> getAllHasRatings(boolean sortRatingAsc,
                                 @NotNull @Max(20L) Integer nRow,
                                 @NotNull Integer pageNumber);

    List<Tuple> getAllHasRatings(@NotNull @Range(max = 5L) Integer lowerBound,
                                 @NotNull @Range(max = 5L) Integer upperBound,
                                 boolean sortRatingAsc,
                                 @NotNull @Max(20L) Integer nRow,
                                 @NotNull Integer pageNumber);

    Map<Tuple, String> getAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                                 boolean sortLastUpdatedAsc,
                                                 @NotNull @Max(20L) Integer nRow,
                                                 @NotNull Integer pageNumber);

    Map<Tuple, String> getAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                                 @NotNull @Range(max = 5L) Integer rating,
                                                 boolean sortLastUpdatedAsc,
                                                 @NotNull @Max(20L) Integer nRow,
                                                 @NotNull Integer pageNumber);

    Map<Integer, Long> getParkingLotRatingCountGroupByRating(@NotNull Long parkingLotId);

    ParkingLotEntity getParkingLotById(@NotNull Long id);

    ParkingLotLimitEntity getParkingLotLimitById(@NotNull Long id);

    Boolean checkAvailability(@NotNull Long parkingLotId);

    List<Long> checkUnavailability(@NotEmpty List<Long> parkingLotIdList);

    List<Tuple> getTopParkingLotInRegionOrderByDistanceWithoutName(@NotNull Double lat,
                                                                   @NotNull Double lng,
                                                                   @NotNull Integer radius,
                                                                   @NotNull Integer nResult);

    List<Tuple> getTopParkingLotInRegionOrderByDistanceWithName(@NotNull Double lat,
                                                                @NotNull Double lng,
                                                                @NotNull Integer radius,
                                                                @NotNull Integer nResult);

    void deleteParkingLotById(@NotNull Long id);

    void deleteMultiParkingLotById(@NotNull Set<Long> parkingLotIdSet);

    void updateAvailability(@NotNull Short newAvailability, @NotNull Long parkingLotId);

    String getParkingLotNameByParkingLotId(@NotNull Long parkingLotId);

    Map<Long, String> mapToParkingLotNameMap(@NotNull Set<Long> parkingLotIdSet);

    Map<Long, Long> countAllParkingLotGroupByType();

    void createNewRating(@NotNull Long parkingLotId, @NotNull Long customerId,
                         @NotNull Integer rating, @NotEmpty String comment);

    void addEmployeeOfParkingLot(@NotNull Long employeeId, @NotNull Long parkingLotId);
}