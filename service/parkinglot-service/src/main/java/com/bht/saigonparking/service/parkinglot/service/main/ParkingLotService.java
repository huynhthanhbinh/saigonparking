package com.bht.saigonparking.service.parkinglot.service.main;

import java.util.List;
import java.util.Map;

import javax.persistence.Tuple;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.bht.saigonparking.service.parkinglot.entity.ParkingLotEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotInformationEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotLimitEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotRatingEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotTypeEntity;

/**
 *
 * @author bht
 */
public interface ParkingLotService {

    Long countAll(@NotEmpty String keyword, boolean isAvailableOnly);

    Long countAll(@NotEmpty String keyword, boolean isAvailableOnly, @NotNull ParkingLotTypeEntity parkingLotTypeEntity);

    Long countAllHasRatings();

    Long countAllHasRatings(@NotNull @Range(max = 5L) Integer lowerBound,
                            @NotNull @Range(max = 5L) Integer upperBound);

    Long countAllRatingsOfParkingLot(@NotNull Long parkingLotId);

    Long countAllRatingsOfParkingLot(@NotNull Long parkingLotId, @NotNull @Range(min = 1L, max = 5L) Integer rating);

    List<ParkingLotEntity> getAll(@NotNull List<Long> parkingLotIdList);

    List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow,
                                  @NotNull Integer pageNumber,
                                  @NotEmpty String keyword,
                                  boolean isAvailableOnly);

    List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow,
                                  @NotNull Integer pageNumber,
                                  @NotEmpty String keyword,
                                  boolean isAvailableOnly,
                                  @NotNull ParkingLotTypeEntity parkingLotTypeEntity);

    List<ParkingLotInformationEntity> getAllHasRatings(boolean sortRatingAsc);

    List<ParkingLotInformationEntity> getAllHasRatings(@NotNull @Range(max = 5L) Integer lowerBound,
                                                       @NotNull @Range(max = 5L) Integer upperBound,
                                                       boolean sortRatingAsc);

    Map<ParkingLotRatingEntity, String> getAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                                                  boolean sortLastUpdatedAsc,
                                                                  @NotNull @Max(20L) Integer nRow,
                                                                  @NotNull Integer pageNumber);

    Map<ParkingLotRatingEntity, String> getAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                                                  @NotNull @Range(min = 1L, max = 5L) Integer rating,
                                                                  boolean sortLastUpdatedAsc,
                                                                  @NotNull @Max(20L) Integer nRow,
                                                                  @NotNull Integer pageNumber);

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

    void deleteMultiParkingLotById(@NotNull List<Long> parkingLotIdList);
}