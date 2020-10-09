package com.web.services.utility.orm.interfaces;

import com.web.services.orm.api.UserAPI;
import com.web.services.orm.entity.login.UserAccount;
import com.web.services.security.UserInformation;

public interface UserUtils extends Utils<UserAccount, UserAPI> {

    UserAccount getUserAccount();

    UserInformation getUserInformation();

    String getUsername();

    Long getAccountID();
}
