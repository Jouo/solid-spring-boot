package com.web.services.orm.service.classes;

import com.web.services.orm.api.UserAPI;
import com.web.services.orm.dao.interfaces.UserAccountDAO;
import com.web.services.orm.entity.login.UserAccount;
import com.web.services.orm.service.interfaces.UserAccountService;
import com.web.services.utility.orm.interfaces.UserUtils;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl
        extends CRUD<UserAccount, UserAPI, UserAccountDAO, UserUtils>
        implements UserAccountService {

    public UserAccountServiceImpl(UserAccountDAO userAccountDAO, UserUtils userUtils) {
        super(userAccountDAO, userUtils);
    }
}
