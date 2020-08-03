package com.bht.saigonparking.service.booking.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author bht
 */
public final class LicensePlateValidator implements ConstraintValidator<LicensePlateValidation, String> {

    @Override
    public boolean isValid(String numberLicensePlate, ConstraintValidatorContext constraintValidatorContext) {
        return numberLicensePlate
                .replaceAll("\\s+|\\.+", "")                 // remove all space and dot characters exist in string
                .matches("^[0-9]{1,2}[A-Za-z][0-9]?-[0-9]{4,5}$");      // example: 59H1-76217, 54L6-2908, 51B-1234, 86B-56789
    }
}