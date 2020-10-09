package com.web.services.utility.orm.classes;

import com.web.services.orm.api.CashRegisterAPI;
import com.web.services.orm.dao.interfaces.PrinterDAO;
import com.web.services.orm.entity.configuration.CashRegister;
import com.web.services.orm.entity.configuration.Printer;
import com.web.services.utility.orm.interfaces.CashRegisterUtils;
import org.springframework.stereotype.Component;

@Component
public class CashRegisterUtilsImpl implements CashRegisterUtils {

    private PrinterDAO printerDAO;

    public CashRegisterUtilsImpl(PrinterDAO printerDAO) {
        this.printerDAO = printerDAO;
    }

    @Override
    public CashRegister convertAPI(CashRegisterAPI api) {
        CashRegister cashRegister = new CashRegister();
        boolean converted = setCashRegister(cashRegister, api);
        if (converted) {
            return cashRegister;
        }
        return null;
    }

    @Override
    public boolean update(CashRegister cashRegister, CashRegisterAPI api) {
        return setCashRegister(cashRegister, api);
    }

    private boolean setCashRegister(CashRegister cashRegister, CashRegisterAPI api) {
        cashRegister.setName(api.getName());
        Printer printer = getPrinter(api);
        if (printer != null) {
            cashRegister.setPrinter(printer);
            return true;
        }
        return false;
    }

    private Printer getPrinter(CashRegisterAPI api) {
        if (api.getPrinter() != null) {
            Long id = api.getPrinter();
            Printer printer = printerDAO.find(id);
            return printer;
        }
        return null;
    }
}
