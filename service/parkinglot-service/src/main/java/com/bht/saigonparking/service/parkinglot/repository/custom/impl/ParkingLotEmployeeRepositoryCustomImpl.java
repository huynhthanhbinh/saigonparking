package com.bht.saigonparking.service.parkinglot.repository.custom.impl;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Repository;

import com.bht.saigonparking.common.base.BaseRepositoryCustom;
import com.bht.saigonparking.service.parkinglot.repository.custom.ParkingLotEmployeeRepositoryCustom;

/**
 *
 * @author bht
 */
@Repository
public class ParkingLotEmployeeRepositoryCustomImpl extends BaseRepositoryCustom implements ParkingLotEmployeeRepositoryCustom {

    @Override
    public Long getParkingLotEmployeeIdOfParkingLot(@NotNull Long parkingLotId) {

        String query = "SELECT PLE.userId " +
                "FROM ParkingLotEmployeeEntity PLE " +
                "WHERE PLE.parkingLotEntity.id = :parkingLotId";

        return entityManager.createQuery(query, Long.class)
                .setParameter("parkingLotId", parkingLotId)
                .getSingleResult();
    }
}