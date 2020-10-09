package com.web.services.validator.intercaces;

import com.web.services.validator.classes.EpochAfterCurrentDateImpl;
import com.web.services.validator.classes.MustExistImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EpochAfterCurrentDateImpl.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EpochAfterCurrentDate {

    String message() default "Ban date was set before current date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
