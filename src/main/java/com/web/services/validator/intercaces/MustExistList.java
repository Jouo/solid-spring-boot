package com.web.services.validator.intercaces;

import com.web.services.validator.classes.MustExistImpl;
import com.web.services.validator.classes.MustExistListImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MustExistListImpl.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MustExistList {

    String className() default "default";

    String message() default "One or more entities don't exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
