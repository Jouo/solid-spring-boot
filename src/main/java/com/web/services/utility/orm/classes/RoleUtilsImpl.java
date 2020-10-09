package com.web.services.utility.orm.classes;

import com.web.services.orm.api.RoleAPI;
import com.web.services.orm.entity.login.AccountRole;
import com.web.services.utility.orm.interfaces.RoleUtils;
import org.springframework.stereotype.Component;

@Component
public class RoleUtilsImpl implements RoleUtils {

    @Override
    public AccountRole convertAPI(RoleAPI api) {
        AccountRole accountRole = new AccountRole();
        setAccountRole(accountRole, api);
        return accountRole;
    }

    @Override
    public boolean update(AccountRole accountRole, RoleAPI api) {
        setAccountRole(accountRole, api);
        return true;
    }

    private void setAccountRole(AccountRole accountRole, RoleAPI api) {
        String role = "ROLE_" + api.getName().toLowerCase();
        accountRole.setRole(role);
        accountRole.setDescription(api.getName());
    }
}
