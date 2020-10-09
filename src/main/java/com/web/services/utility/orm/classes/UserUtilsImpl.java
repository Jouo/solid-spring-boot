package com.web.services.utility.orm.classes;

import com.web.services.orm.api.UserAPI;
import com.web.services.orm.dao.interfaces.APIBinderDAO;
import com.web.services.orm.dao.interfaces.AccountRoleDAO;
import com.web.services.orm.entity.login.AccountDetails;
import com.web.services.orm.entity.login.AccountRole;
import com.web.services.orm.entity.login.UserAccount;
import com.web.services.orm.service.interfaces.AccountDetailsService;
import com.web.services.security.UserInformation;
import com.web.services.utility.orm.interfaces.UserUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserUtilsImpl implements UserUtils {

    private PasswordEncoder encoder;
    private AccountRoleDAO accountRoleDAO;
    private APIBinderDAO apiBinderDAO;

    public UserUtilsImpl(PasswordEncoder encoder, AccountRoleDAO accountRoleDAO, APIBinderDAO apiBinderDAO) {
        this.encoder = encoder;
        this.accountRoleDAO = accountRoleDAO;
        this.apiBinderDAO = apiBinderDAO;
    }

    @Override
    public UserAccount convertAPI(UserAPI api) {
        UserAccount userAccount = new UserAccount(new AccountDetails());
        boolean converted = setUserAccount(userAccount, api);
        if (converted) {
            return userAccount;
        }
        return null;
    }

    @Override
    public boolean update(UserAccount userAccount, UserAPI api) {
        return setUserAccount(userAccount, api);
    }

    @Override
    public Long getAccountID() {
        UserAccount userAccount = getUserAccount();
        if (userAccount != null) {
            return userAccount.getAccountDetails().getId();
        }
        return null;
    }

    @Override
    public String getUsername() {
        UserInformation userInformation = getUserInformation();
        if (userInformation != null) {
            return userInformation.getUsername();
        }
        return "N / A";
    }

    @Override
    public UserAccount getUserAccount() {
        UserInformation userInformation = getUserInformation();
        if ((userInformation != null) && (userInformation.getUserAccount() != null)) {
            return userInformation.getUserAccount();
        }
        return null;
    }

    @Override
    public UserInformation getUserInformation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication != null)
                && (!(authentication.getPrincipal() instanceof String))) {
            return (UserInformation) authentication.getPrincipal();
        }
        return null;
    }

    private boolean setUserAccount(UserAccount userAccount, UserAPI api) {
        List<AccountRole> accountRoles = getAccountRoles(api);
        boolean isValidUsername = isValidUsername(userAccount, api);

        if (isValidUsername && (!accountRoles.isEmpty())) {
            String password = encoder.encode(api.getPassword());
            userAccount.setName(api.getName());
            userAccount.setLastName(api.getLastName());
            userAccount.getAccountDetails().setPassword(password);
            userAccount.getAccountDetails().setUsername(api.getUsername());
            userAccount.getAccountDetails().setRoles(accountRoles);
            return true;
        }
        return false;
    }

    private boolean isValidUsername(UserAccount account, UserAPI api) {
        AccountDetails details = account.getAccountDetails();
        String newUsername = api.getUsername();
        String currentUsername = details.getUsername();

        if (!currentUsername.isBlank()) {
            if (currentUsername.equals(newUsername)) { return true; }
        }
        return !apiBinderDAO.exists("AccountDetails", "username", newUsername);
    }

    private List<AccountRole> getAccountRoles(UserAPI api) {
        List<AccountRole> accountRoles = new ArrayList<>();
        for (Long id : api.getRoles()) {
            AccountRole accountRole = accountRoleDAO.find(id);
            if ((accountRole != null) && !(accountRoles.contains(accountRole))) {
                accountRoles.add(accountRole);
            }
        }
        return accountRoles;
    }
}
