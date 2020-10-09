package com.web.services.orm.dao.classes;

import com.web.services.orm.dao.interfaces.UserAccountDAO;
import com.web.services.orm.entity.login.UserAccount;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserAccountDAOImpl extends CRUD<UserAccount> implements UserAccountDAO {

    public UserAccountDAOImpl() {
        super(UserAccount.class);
    }
}
