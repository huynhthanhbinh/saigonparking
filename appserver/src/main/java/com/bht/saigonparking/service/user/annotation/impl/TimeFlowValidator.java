package com.bht.saigonparking.service.user.annotation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.bht.saigonparking.service.user.annotation.TimeFlowValidation;
import com.bht.saigonparking.service.user.entity.parkinglot.ParkingLotEntity;

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