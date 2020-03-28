package com.bht.parkingmap.appserver.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.bht.parkingmap.appserver.annotation.impl.TimeFlowValidator;

/**
 *
 * @author bht
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TimeFlowValidator.class)
public @interface TimeFlowValidation {

    String message() default "Closing hour cannot be earlier than opening hour !";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}