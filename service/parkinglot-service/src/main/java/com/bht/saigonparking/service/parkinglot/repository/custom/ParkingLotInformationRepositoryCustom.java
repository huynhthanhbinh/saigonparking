package com.bht.saigonparking.service.parkinglot.repository.custom;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.bht.saigonparking.service.parkinglot.entity.ParkingLotInformationEntity;

/**
 *
 * @author bht
 */
public interface ParkingLotInformationRepositoryCustom {

    List<ParkingLotInformationEntity> getAllHasRatings(boolean sortRatingAsc);

    List<ParkingLotInformationEntity> getAllHasRatings(@NotNull @Range(max = 5L) Integer lowerBound,
                                                       @NotNull @Range(max = 5L) Integer upperBound,
                                                       boolean sortRatingAsc);
}