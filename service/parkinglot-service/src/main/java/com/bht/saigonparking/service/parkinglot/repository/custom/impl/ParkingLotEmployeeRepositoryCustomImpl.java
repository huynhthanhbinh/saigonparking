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
    public Long getParkingLotIdByParkingLotEmployeeId(@NotNull Long parkingLotEmployeeId) {

        String query = "SELECT PLE.parkingLotEntity.id " +
                "FROM ParkingLotEmployeeEntity PLE " +
                "WHERE PLE.userId = :parkingLotEmployeeId";

        return entityManager.createQuery(query, Long.class)
                .setParameter("parkingLotEmployeeId", parkingLotEmployeeId)
                .getSingleResult();
    }
}