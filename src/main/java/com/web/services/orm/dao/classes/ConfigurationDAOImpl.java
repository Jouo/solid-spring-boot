package com.web.services.orm.dao.classes;

import com.web.services.orm.dao.interfaces.ConfigurationDAO;
import com.web.services.orm.entity.configuration.Configuration;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ConfigurationDAOImpl extends CRUD<Configuration> implements ConfigurationDAO {

    public ConfigurationDAOImpl() {
        super(Configuration.class);
    }
}
