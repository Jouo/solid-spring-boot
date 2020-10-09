package com.web.services.validator.classes;

import com.web.services.orm.dao.interfaces.APIBinderDAO;
import com.web.services.validator.intercaces.MustExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MustExistImpl
        implements ConstraintValidator<MustExist, Long> {

    @Autowired
    private APIBinderDAO apiBinderDAO;
    private String table;

    @Override
    public void initialize(MustExist annotation) {
        this.table = annotation.className();
    }

    @Override
    public boolean isValid(Long id,
            ConstraintValidatorContext constraintValidatorContext) {

        return apiBinderDAO.exists(this.table, id);
    }
}
