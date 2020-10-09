package com.web.services.orm.service.classes;

import com.web.services.orm.api.PrinterAPI;
import com.web.services.orm.dao.interfaces.PrinterDAO;
import com.web.services.orm.entity.configuration.Printer;
import com.web.services.orm.service.interfaces.PrinterService;
import com.web.services.utility.orm.interfaces.PrinterUtils;
import org.springframework.stereotype.Service;

@Service
public class PrinterServiceImpl
        extends CRUD<Printer, PrinterAPI, PrinterDAO, PrinterUtils>
        implements PrinterService {

    public PrinterServiceImpl(PrinterDAO printerDAO, PrinterUtils printerUtils) {
        super(printerDAO, printerUtils);
    }
}
