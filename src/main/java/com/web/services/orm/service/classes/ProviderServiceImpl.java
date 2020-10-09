package com.web.services.orm.service.classes;

import com.web.services.orm.api.ProviderAPI;
import com.web.services.orm.dao.interfaces.ProviderDAO;
import com.web.services.orm.entity.configuration.Provider;
import com.web.services.orm.service.interfaces.ProviderService;
import com.web.services.utility.orm.interfaces.ProviderUtils;
import org.springframework.stereotype.Service;

@Service
public class ProviderServiceImpl
        extends CRUD<Provider, ProviderAPI, ProviderDAO, ProviderUtils>
        implements ProviderService {

    public ProviderServiceImpl(ProviderDAO providerDAO, ProviderUtils providerUtils) {
        super(providerDAO, providerUtils);
    }
}
