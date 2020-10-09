package com.web.services.orm.dao.classes;

import com.web.services.orm.dao.interfaces.AccountRoleDAO;
import com.web.services.orm.entity.login.AccountRole;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class AccountRoleDAOImpl extends CRUD<AccountRole> implements AccountRoleDAO {

    public AccountRoleDAOImpl() {
        super(AccountRole.class);
    }
}
