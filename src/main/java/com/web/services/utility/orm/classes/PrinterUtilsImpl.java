package com.web.services.utility.orm.classes;

import com.web.services.orm.api.PrinterAPI;
import com.web.services.orm.entity.configuration.Printer;
import com.web.services.utility.orm.interfaces.PrinterUtils;
import org.springframework.stereotype.Component;

@Component
public class PrinterUtilsImpl implements PrinterUtils {

    @Override
    public Printer convertAPI(PrinterAPI api) {
        Printer printer = new Printer();
        setPrinter(printer, api);
        return printer;
    }

    @Override
    public boolean update(Printer printer, PrinterAPI api) {
        setPrinter(printer, api);
        return true;
    }

    private void setPrinter(Printer printer, PrinterAPI api) {
        printer.setName(api.getName());
        printer.setIp(api.getIp());
    }
}
