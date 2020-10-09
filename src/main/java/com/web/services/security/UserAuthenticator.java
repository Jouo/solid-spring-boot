package com.web.services.security;

import com.web.services.Setting;
import com.web.services.orm.dao.interfaces.AccountDetailsDAO;
import com.web.services.orm.entity.login.AccountDetails;
import com.web.services.orm.entity.login.UserAccount;
import com.web.services.orm.service.interfaces.AccountDetailsService;
import com.web.services.utility.general.interfaces.AuthenticatorUtils;
import com.web.services.utility.orm.interfaces.UserUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Primary
@Component
public class UserAuthenticator implements UserDetailsService {

    private AccountDetailsService accountService;
    private AccountDetailsDAO accountDAO;
    private AuthenticatorUtils utils;

    public UserAuthenticator(AccountDetailsService accountService, AccountDetailsDAO accountDAO,
                             AuthenticatorUtils utils) {
        this.accountService = accountService;
        this.accountDAO = accountDAO;
        this.utils = utils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        UserAccount userAccount = accountDAO.getUserAccount(username);
        if (userAccount == null) {
            throw new AuthenticationException(Setting.AUTH_USER_NOT_FOUND_MESSAGE) {};
        }

        AccountDetails accountDetails = userAccount.getAccountDetails();
        if (((accountDetails.getRoles() == null) || accountDetails.getRoles().isEmpty())) {
            throw new AuthenticationException(Setting.AUTH_ROLE_NOT_FOUND_MESSAGE) {};
        }

        boolean stillBanned = utils.isBanned(accountDetails);
        if (!stillBanned && (accountDetails.getBanTime() != 0L)) {
            accountService.unban(accountDetails.getId());
            userAccount = accountDAO.getUserAccount(username);
        }
        return new UserInformation(userAccount);
    }
}
