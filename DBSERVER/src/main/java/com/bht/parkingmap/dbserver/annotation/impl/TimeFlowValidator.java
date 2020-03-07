package com.bht.parkingmap.dbserver.annotation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.bht.parkingmap.dbserver.annotation.TimeFlowValidation;
import com.bht.parkingmap.dbserver.entity.parkinglot.ParkingLotEntity;

/**
 *
 * @author bht
 */
public final class TimeFlowValidator implements ConstraintValidator<TimeFlowValidation, ParkingLotEntity> {

    @Override
    public boolean isValid(ParkingLotEntity parkingLotEntity, ConstraintValidatorContext constraintValidatorContext) {
        return parkingLotEntity.getClosingHour().after(parkingLotEntity.getOpeningHour());
    }
}