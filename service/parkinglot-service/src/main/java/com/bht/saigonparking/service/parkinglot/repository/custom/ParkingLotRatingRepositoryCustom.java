package com.bht.saigonparking.service.parkinglot.repository.custom;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.bht.saigonparking.service.parkinglot.entity.ParkingLotRatingEntity;

/**
 *
 * @author bht
 */
public interface ParkingLotRatingRepositoryCustom {

    Long countAllRatingsOfParkingLot(@NotNull Long parkingLotId);

    Long countAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                     @NotNull @Range(min = 1L, max = 5L) Integer rating);

    List<ParkingLotRatingEntity> getAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                                           boolean sortLastUpdatedAsc,
                                                           @NotNull @Max(20L) Integer nRow,
                                                           @NotNull Integer pageNumber);

    List<ParkingLotRatingEntity> getAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                                           @NotNull @Range(min = 1L, max = 5L) Integer rating,
                                                           boolean sortLastUpdatedAsc,
                                                           @NotNull @Max(20L) Integer nRow,
                                                           @NotNull Integer pageNumber);

    Map<Integer, Long> getParkingLotRatingCountGroupByRating(@NotNull Long parkingLotId);
}