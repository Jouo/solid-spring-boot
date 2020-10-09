package com.web.services.utility.general.interfaces;

import com.web.services.orm.entity.login.AccountDetails;

public interface AuthenticatorUtils {

    boolean isBanned(AccountDetails account);
}
