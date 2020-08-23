package com.bht.saigonparking.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.bht.saigonparking.common.annotation.UuidStringValidation;

/**
 *
 * @author bht
 */
public final class UuidStringValidator implements ConstraintValidator<UuidStringValidation, String> {

    @Override
    public boolean isValid(String uuidString, ConstraintValidatorContext constraintValidatorContext) {
        return uuidString.matches("^[A-Za-z0-9]{8}-[A-Za-z0-9]{4}-[A-Za-z0-9]{4}-[A-Za-z0-9]{4}-[A-Za-z0-9]{12}$");
    }
}