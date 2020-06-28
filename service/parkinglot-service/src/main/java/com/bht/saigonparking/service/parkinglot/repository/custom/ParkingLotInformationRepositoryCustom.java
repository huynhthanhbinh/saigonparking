package com.bht.saigonparking.service.parkinglot.repository.custom;

import java.util.List;

import javax.persistence.Tuple;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

/**
 *
 * @author bht
 */
public interface ParkingLotInformationRepositoryCustom {

    Long countAllHasRatings();

    Long countAllHasRatings(@NotNull @Range(max = 5L) Integer lowerBound,
                            @NotNull @Range(max = 5L) Integer upperBound);

    List<Tuple> getAllHasRatings(boolean sortRatingAsc,
                                 @NotNull @Max(20L) Integer nRow,
                                 @NotNull Integer pageNumber);

    List<Tuple> getAllHasRatings(@NotNull @Range(max = 5L) Integer lowerBound,
                                 @NotNull @Range(max = 5L) Integer upperBound,
                                 boolean sortRatingAsc,
                                 @NotNull @Max(20L) Integer nRow,
                                 @NotNull Integer pageNumber);
}