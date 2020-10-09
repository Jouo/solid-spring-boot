package com.web.services.validator.classes;

import com.web.services.orm.dao.interfaces.APIBinderDAO;
import com.web.services.validator.intercaces.MustExistList;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class MustExistListImpl
        implements ConstraintValidator<MustExistList, List<Long>> {

    @Autowired
    private APIBinderDAO apiBinderDAO;
    private String table;

    @Override
    public void initialize(MustExistList annotation) {
        this.table = annotation.className();
    }

    @Override
    public boolean isValid(List<Long> ids,
                           ConstraintValidatorContext constraintValidatorContext) {

        for (Long id : ids) {
            boolean exists = apiBinderDAO.exists(this.table, id);
            if (!exists) { return false; }
        }
        return true;
    }
}
