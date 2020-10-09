package com.web.services.orm.service.classes;

import com.web.services.orm.api.CashRegisterAPI;
import com.web.services.orm.dao.interfaces.CashRegisterDAO;
import com.web.services.orm.entity.configuration.CashRegister;
import com.web.services.orm.service.interfaces.CashRegisterService;
import com.web.services.utility.orm.interfaces.CashRegisterUtils;
import org.springframework.stereotype.Service;

@Service
public class CashRegisterServiceImpl
        extends CRUD<CashRegister, CashRegisterAPI, CashRegisterDAO, CashRegisterUtils>
        implements CashRegisterService {

    public CashRegisterServiceImpl(CashRegisterDAO cashRegisterDAO, CashRegisterUtils cashRegisterUtils) {
        super(cashRegisterDAO, cashRegisterUtils);
    }
}
