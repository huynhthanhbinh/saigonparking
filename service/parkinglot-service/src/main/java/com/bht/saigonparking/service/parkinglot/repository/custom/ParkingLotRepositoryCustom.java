package com.bht.saigonparking.service.parkinglot.repository.custom;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.bht.saigonparking.service.parkinglot.entity.ParkingLotEntity;

/**
 *
 * @author bht
 */
public interface ParkingLotRepositoryCustom {

    Long countAll();

    List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber);
}