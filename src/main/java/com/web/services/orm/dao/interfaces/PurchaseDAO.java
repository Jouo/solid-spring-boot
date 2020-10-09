package com.web.services.orm.dao.interfaces;

import com.web.services.orm.entity.login.UserAccount;
import com.web.services.orm.entity.transaction.Purchase;

import java.util.List;

public interface PurchaseDAO extends DAO<Purchase> {

    List<Purchase> getUnchecked(UserAccount userAccount);

    List<Purchase> getChecked();
}
