package com.web.services.orm.service.classes;

import com.web.services.orm.api.RoleAPI;
import com.web.services.orm.dao.interfaces.AccountRoleDAO;
import com.web.services.orm.entity.login.AccountRole;
import com.web.services.orm.service.interfaces.AccountRoleService;
import com.web.services.utility.orm.interfaces.RoleUtils;
import org.springframework.stereotype.Service;

@Service
public class AccountRoleServiceImpl
        extends CRUD<AccountRole, RoleAPI, AccountRoleDAO, RoleUtils>
        implements AccountRoleService {

    public AccountRoleServiceImpl(AccountRoleDAO accountRoleDAO, RoleUtils roleUtils) {
        super(accountRoleDAO, roleUtils);
    }
}
