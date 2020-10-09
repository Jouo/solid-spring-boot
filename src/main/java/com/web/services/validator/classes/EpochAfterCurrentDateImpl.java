package com.web.services.validator.classes;

import com.web.services.validator.intercaces.EpochAfterCurrentDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;

public class EpochAfterCurrentDateImpl
        implements ConstraintValidator<EpochAfterCurrentDate, Long> {

    @Override
    public void initialize(EpochAfterCurrentDate annotation) {}

    @Override
    public boolean isValid(Long time,
            ConstraintValidatorContext constraintValidatorContext) {

        Instant epochDate = Instant.ofEpochSecond(time);
        Instant currentTime = Instant.now();
        return epochDate.isAfter(currentTime);
    }
}
