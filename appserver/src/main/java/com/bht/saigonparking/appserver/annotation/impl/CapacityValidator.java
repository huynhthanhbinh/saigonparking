package com.bht.saigonparking.appserver.annotation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.bht.saigonparking.appserver.annotation.CapacityValidation;
import com.bht.saigonparking.appserver.entity.parkinglot.ParkingLotLimitEntity;

/**
 *
 * @author bht
 */
public final class CapacityValidator implements ConstraintValidator<CapacityValidation, ParkingLotLimitEntity> {

    @Override
    public boolean isValid(ParkingLotLimitEntity parkingLotLimitEntity, ConstraintValidatorContext constraintValidatorContext) {
        return parkingLotLimitEntity.getAvailableSlot().compareTo((short) 0) >= 0 &&
                parkingLotLimitEntity.getAvailableSlot().compareTo(parkingLotLimitEntity.getTotalSlot()) <= 0;
    }
}