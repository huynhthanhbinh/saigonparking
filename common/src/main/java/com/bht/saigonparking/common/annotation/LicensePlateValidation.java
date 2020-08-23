package com.bht.saigonparking.common.annotation;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.bht.saigonparking.common.validator.LicensePlateValidator;

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