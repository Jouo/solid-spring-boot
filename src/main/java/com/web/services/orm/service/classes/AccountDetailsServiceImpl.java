package com.web.services.orm.service.classes;

import com.web.services.orm.api.EpochBanDateAPI;
import com.web.services.orm.dao.interfaces.AccountDetailsDAO;
import com.web.services.orm.entity.login.AccountDetails;
import com.web.services.orm.entity.login.UserAccount;
import com.web.services.orm.service.interfaces.AccountDetailsService;
import com.web.services.utility.orm.interfaces.UserUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;

@Service
public class AccountDetailsServiceImpl implements AccountDetailsService {

    private AccountDetailsDAO accountDetailsDAO;
    private UserUtils userUtils;

    public AccountDetailsServiceImpl(AccountDetailsDAO accountDetailsDAO, UserUtils userUtils) {
        this.accountDetailsDAO = accountDetailsDAO;
        this.userUtils = userUtils;
    }

    @Override
    @Transactional
    public UserAccount getUserAccount(String username) {
        return accountDetailsDAO.getUserAccount(username);
    }

    @Override
    @Transactional
    public boolean unban(Long id) {
        AccountDetails accountDetails = accountDetailsDAO.find(id);
        if (accountDetails != null) {
            accountDetailsDAO.unban(id);
            accountDetailsDAO.refresh(accountDetails);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean ban(Long id, EpochBanDateAPI api) {
        Long currentID = userUtils.getAccountID();
        AccountDetails accountDetails = accountDetailsDAO.find(id);
        AccountDetails currentAccount = accountDetailsDAO.find(currentID);
        if ((accountDetails != null) && (!accountDetails.equals(currentAccount))) {
            Instant banTime = Instant.ofEpochSecond(api.getDate());
            Instant currentTime = Instant.now();

            if (banTime.isAfter(currentTime)) {
                accountDetails.setBanTime(api.getDate());
                accountDetailsDAO.merge(accountDetails);
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public boolean toggleLock(Long id) {
        Long currentID = userUtils.getAccountID();
        AccountDetails accountDetails = accountDetailsDAO.find(id);
        AccountDetails currentAccount = accountDetailsDAO.find(currentID);
        if ((accountDetails != null) && (!accountDetails.equals(currentAccount))) {
            boolean isLocked = accountDetails.isLocked();
            if (isLocked) { accountDetails.setLocked(false); }
            else { accountDetails.setLocked(true); }

            accountDetailsDAO.merge(accountDetails);
            return true;
        }
        return false;
    }
}
