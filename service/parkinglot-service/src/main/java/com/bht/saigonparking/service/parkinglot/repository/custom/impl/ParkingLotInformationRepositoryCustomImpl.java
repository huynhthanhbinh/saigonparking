package com.bht.saigonparking.service.parkinglot.repository.custom.impl;

import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.common.base.BaseRepositoryCustom;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotInformationEntity;
import com.bht.saigonparking.service.parkinglot.repository.custom.ParkingLotInformationRepositoryCustom;

/**
 *
 * @author bht
 */
@Repository
public class ParkingLotInformationRepositoryCustomImpl extends BaseRepositoryCustom implements ParkingLotInformationRepositoryCustom {

    @Override
    public List<ParkingLotInformationEntity> getAllHasRatings(boolean sortRatingAsc) {
        return Collections.emptyList();
    }

    @Override
    public List<ParkingLotInformationEntity> getAllHasRatings(@NotNull @Range(max = 5L) Integer lowerBound,
                                                              @NotNull @Range(max = 5L) Integer upperBound,
                                                              boolean sortRatingAsc) {
        return Collections.emptyList();
    }
}