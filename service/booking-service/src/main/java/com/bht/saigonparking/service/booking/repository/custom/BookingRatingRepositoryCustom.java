package com.bht.saigonparking.service.booking.repository.custom;

import java.util.List;
import java.util.Map;

import javax.persistence.Tuple;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

/**
 *
 * @author bht
 */
public interface BookingRatingRepositoryCustom {

    Long countAllRatingsOfParkingLot(@NotNull Long parkingLotId);

    Long countAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                     @NotNull @Range(min = 1L, max = 5L) Integer rating);

    List<Tuple> getAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                          boolean sortLastUpdatedAsc,
                                          @NotNull @Max(20L) Integer nRow,
                                          @NotNull Integer pageNumber);

    List<Tuple> getAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                          @NotNull @Range(min = 1L, max = 5L) Integer rating,
                                          boolean sortLastUpdatedAsc,
                                          @NotNull @Max(20L) Integer nRow,
                                          @NotNull Integer pageNumber);

    Map<Integer, Long> getParkingLotRatingCountGroupByRating(@NotNull Long parkingLotId);
}