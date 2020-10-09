package com.web.services.orm.service.interfaces;

import com.web.services.orm.api.EpochBanDateAPI;
import com.web.services.orm.entity.login.UserAccount;

public interface AccountDetailsService  {

    UserAccount getUserAccount(String username);

    boolean unban(Long id);

    boolean ban(Long id, EpochBanDateAPI api);

    boolean toggleLock(Long id);
}
