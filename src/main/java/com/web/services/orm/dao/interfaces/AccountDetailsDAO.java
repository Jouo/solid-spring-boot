package com.web.services.orm.dao.interfaces;

import com.web.services.orm.entity.login.AccountDetails;
import com.web.services.orm.entity.login.UserAccount;

public interface AccountDetailsDAO extends DAO<AccountDetails> {

    UserAccount getUserAccount(String username);

    void unban(Long id);
}
