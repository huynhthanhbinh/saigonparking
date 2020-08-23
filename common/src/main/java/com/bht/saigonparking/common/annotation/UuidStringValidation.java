package com.bht.saigonparking.common.annotation;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotEmpty;

import com.bht.saigonparking.common.validator.UuidStringValidator;

/**
 *
 * @author bht
 */
@NotEmpty
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UuidStringValidator.class)
@Target({FIELD, METHOD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
public @interface UuidStringValidation {

    String message() default "UUID string is in wrong format !";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}