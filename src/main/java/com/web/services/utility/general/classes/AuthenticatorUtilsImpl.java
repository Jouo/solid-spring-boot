package com.web.services.utility.general.classes;

import com.web.services.orm.entity.login.AccountDetails;
import com.web.services.orm.entity.login.UserAccount;
import com.web.services.utility.general.interfaces.AuthenticatorUtils;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class AuthenticatorUtilsImpl implements AuthenticatorUtils {

    @Override
    public boolean isBanned(AccountDetails account) {
        Long time = account.getBanTime();

        if (time > 0L) {
            Instant now = Instant.now();
            Instant banTime = Instant.ofEpochSecond(time);

            if (now.isBefore(banTime)) {
                return true;
            }
        }
        return false;
    }
}
