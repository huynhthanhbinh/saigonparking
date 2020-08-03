package com.bht.saigonparking.service.booking.annotation;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author bht
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LicensePlateValidator.class)
@Target({FIELD, METHOD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
public @interface LicensePlateValidation {

    String message() default "Number license plate is in wrong format !";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}