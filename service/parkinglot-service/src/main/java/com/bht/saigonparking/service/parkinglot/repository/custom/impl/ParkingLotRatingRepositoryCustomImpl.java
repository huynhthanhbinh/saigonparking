package com.bht.saigonparking.service.parkinglot.repository.custom.impl;

import java.util.Collections;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.common.base.BaseRepositoryCustom;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotRatingEntity;
import com.bht.saigonparking.service.parkinglot.repository.custom.ParkingLotRatingRepositoryCustom;

/**
 *
 * @author bht
 */
@Repository
public class ParkingLotRatingRepositoryCustomImpl extends BaseRepositoryCustom implements ParkingLotRatingRepositoryCustom {

    @Override
    public List<ParkingLotRatingEntity> getAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                                                  boolean sortLastUpdatedAsc,
                                                                  @NotNull @Max(20L) Integer nRow,
                                                                  @NotNull Integer pageNumber) {
        return Collections.emptyList();
    }

    @Override
    public List<ParkingLotRatingEntity> getAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                                                  @NotNull @Range(min = 1L, max = 5L) Integer rating,
                                                                  boolean sortLastUpdatedAsc,
                                                                  @NotNull @Max(20L) Integer nRow,
                                                                  @NotNull Integer pageNumber) {
        return Collections.emptyList();
    }
}