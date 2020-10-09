package com.web.services.orm.service.classes;

import com.web.services.orm.api.ConfigurationAPI;
import com.web.services.orm.dao.interfaces.ConfigurationDAO;
import com.web.services.orm.entity.configuration.Configuration;
import com.web.services.orm.service.interfaces.ConfigurationService;
import com.web.services.utility.orm.interfaces.ConfigurationUtils;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationServiceImpl
        extends CRUD<Configuration, ConfigurationAPI, ConfigurationDAO, ConfigurationUtils>
        implements ConfigurationService {

    public ConfigurationServiceImpl(ConfigurationDAO configurationDAO, ConfigurationUtils configurationUtils) {
        super(configurationDAO, configurationUtils);
    }
}
