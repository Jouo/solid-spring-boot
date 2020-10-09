package com.web.services.utility.orm.classes;

import com.web.services.orm.api.ConfigurationAPI;
import com.web.services.orm.entity.configuration.Configuration;
import com.web.services.utility.orm.interfaces.ConfigurationUtils;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationUtilsImpl implements ConfigurationUtils {

    @Override
    public Configuration convertAPI(ConfigurationAPI api) {
        Configuration configuration = new Configuration();
        setConfiguration(configuration, api);
        return configuration;
    }

    @Override
    public boolean update(Configuration configuration, ConfigurationAPI api) {
        setConfiguration(configuration, api);
        return true;
    }

    private void setConfiguration(Configuration configuration, ConfigurationAPI api) {
        configuration.setCompany(api.getCompany());
        configuration.setAddress(api.getAddress());
        configuration.setPhone(api.getPhone());
        configuration.setMessage(api.getMessage());
    }
}
