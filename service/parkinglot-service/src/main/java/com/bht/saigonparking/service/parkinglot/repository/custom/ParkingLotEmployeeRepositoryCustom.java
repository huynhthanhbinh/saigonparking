package com.bht.saigonparking.service.parkinglot.repository.custom;

import javax.validation.constraints.NotNull;

/**
 *
 * @author bht
 */
public interface ParkingLotEmployeeRepositoryCustom {

    Long getParkingLotEmployeeIdOfParkingLot(@NotNull Long parkingLotId);
}