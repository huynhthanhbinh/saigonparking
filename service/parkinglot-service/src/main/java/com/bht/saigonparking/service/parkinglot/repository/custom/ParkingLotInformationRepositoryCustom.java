package com.bht.saigonparking.service.parkinglot.repository.custom;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.bht.saigonparking.service.parkinglot.entity.ParkingLotInformationEntity;

/**
 *
 * @author bht
 */
public interface ParkingLotInformationRepositoryCustom {

    Long countAllHasRatings();

    Long countAllHasRatings(@NotNull @Range(max = 5L) Integer lowerBound,
                            @NotNull @Range(max = 5L) Integer upperBound);

    List<ParkingLotInformationEntity> getAllHasRatings(boolean sortRatingAsc,
                                                       @NotNull @Max(20L) Integer nRow,
                                                       @NotNull Integer pageNumber);

    List<ParkingLotInformationEntity> getAllHasRatings(@NotNull @Range(max = 5L) Integer lowerBound,
                                                       @NotNull @Range(max = 5L) Integer upperBound,
                                                       boolean sortRatingAsc,
                                                       @NotNull @Max(20L) Integer nRow,
                                                       @NotNull Integer pageNumber);
}