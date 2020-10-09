package com.web.services.orm.dao.classes;

import com.web.services.orm.dao.interfaces.PrinterDAO;
import com.web.services.orm.entity.configuration.Printer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class PrinterDAOImpl extends CRUD<Printer> implements PrinterDAO {

    public PrinterDAOImpl() {
        super(Printer.class);
    }
}
