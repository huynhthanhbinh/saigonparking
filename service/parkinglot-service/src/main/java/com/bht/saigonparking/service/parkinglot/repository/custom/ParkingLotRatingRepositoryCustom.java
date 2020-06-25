package com.bht.saigonparking.service.parkinglot.repository.custom;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.bht.saigonparking.service.parkinglot.entity.ParkingLotRatingEntity;

/**
 *
 * @author bht
 */
public interface ParkingLotRatingRepositoryCustom {

    List<ParkingLotRatingEntity> getAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                                           boolean sortLastUpdatedAsc,
                                                           @NotNull @Max(20L) Integer nRow,
                                                           @NotNull Integer pageNumber);

    List<ParkingLotRatingEntity> getAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                                           @NotNull @Range(min = 1L, max = 5L) Integer rating,
                                                           boolean sortLastUpdatedAsc,
                                                           @NotNull @Max(20L) Integer nRow,
                                                           @NotNull Integer pageNumber);
}