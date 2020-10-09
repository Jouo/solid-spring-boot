package com.web.services.orm.dao.classes;

import com.web.services.orm.dao.interfaces.AccountDetailsDAO;
import com.web.services.orm.entity.login.AccountDetails;
import com.web.services.orm.entity.login.UserAccount;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
public class AccountDetailsDAOImpl extends CRUD<AccountDetails> implements AccountDetailsDAO {

    public AccountDetailsDAOImpl() {
        super(AccountDetails.class);
    }

    @Override
    public UserAccount getUserAccount(String username) {
        try {
            Query query = super.entityManager.createQuery(String.format(
                    "SELECT userAccount FROM %s WHERE username = :value",
                    AccountDetails.class.getSimpleName()))
                    .setParameter("value", username);

            return (UserAccount) query.getSingleResult();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public void unban(Long id) {
        try {
            Query query = super.entityManager.createQuery(String.format(
                    "UPDATE %s SET banTime = 0 WHERE id = :id",
                    AccountDetails.class.getSimpleName()))
                    .setParameter("id", id);

            query.executeUpdate();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }


}
