package com.web.services.orm.dao.classes;

import com.web.services.orm.dao.interfaces.ProviderDAO;
import com.web.services.orm.entity.configuration.Provider;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ProviderDAOImpl extends CRUD<Provider> implements ProviderDAO {

    public ProviderDAOImpl() {
        super(Provider.class);
    }
}
