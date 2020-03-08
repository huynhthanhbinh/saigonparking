package com.bht.parkingmap.dbserver.annotation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.bht.parkingmap.dbserver.annotation.CapacityValidation;
import com.bht.parkingmap.dbserver.entity.parkinglot.ParkingLotEntity;

/**
 *
 * @author bht
 */
public final class CapacityValidator implements ConstraintValidator<CapacityValidation, ParkingLotEntity> {

    @Override
    public boolean isValid(ParkingLotEntity parkingLotInformationEntity, ConstraintValidatorContext constraintValidatorContext) {
        return parkingLotInformationEntity.getAvailableSlot().compareTo((short) 0) >= 0 &&
                parkingLotInformationEntity.getAvailableSlot().compareTo(parkingLotInformationEntity.getTotalSlot()) <= 0;
    }
}