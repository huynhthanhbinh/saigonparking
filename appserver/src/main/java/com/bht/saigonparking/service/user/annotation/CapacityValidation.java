package com.bht.saigonparking.service.user.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.bht.saigonparking.service.user.annotation.impl.CapacityValidator;

/**
 *
 * @author bht
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CapacityValidator.class)
public @interface CapacityValidation {

    String message() default "Availability cannot be larger than Capacity !";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}