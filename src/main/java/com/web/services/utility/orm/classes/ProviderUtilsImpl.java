package com.web.services.utility.orm.classes;

import com.web.services.orm.api.ProviderAPI;
import com.web.services.orm.entity.configuration.Provider;
import com.web.services.utility.orm.interfaces.ProviderUtils;
import org.springframework.stereotype.Component;

@Component
public class ProviderUtilsImpl implements ProviderUtils {

    @Override
    public Provider convertAPI(ProviderAPI api) {
        Provider provider = new Provider();
        setProvider(provider, api);
        return provider;
    }

    @Override
    public boolean update(Provider provider, ProviderAPI api) {
        setProvider(provider, api);
        return true;
    }

    private void setProvider(Provider provider, ProviderAPI api) {
        provider.setName(api.getName());
        provider.setCompany(api.getCompany());
        provider.setPhone(api.getPhone());
    }
}
