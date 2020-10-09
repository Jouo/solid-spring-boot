package com.web.services.orm.dao.interfaces;

import com.web.services.orm.entity.login.UserAccount;
import com.web.services.orm.entity.transaction.Sale;

import java.util.List;

public interface SaleDAO extends DAO<Sale> {

    List<Sale> getUnchecked(UserAccount userAccount);

    List<Sale> getChecked();
}
