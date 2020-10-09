package com.web.services.orm.dao.classes;

import com.web.services.orm.dao.interfaces.CashRegisterDAO;
import com.web.services.orm.entity.configuration.CashRegister;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CashRegisterDAOImpl extends CRUD<CashRegister> implements CashRegisterDAO {

    public CashRegisterDAOImpl() {
        super(CashRegister.class);
    }
}
