package com.web.services.orm.dao.interfaces;

import com.web.services.orm.entity.login.UserAccount;
import com.web.services.orm.entity.transaction.Checkout;

import java.util.List;

public interface CheckoutDAO extends DAO<Checkout> {

    List<Checkout> getByUser(UserAccount userAccount);
}
