package com.web.services.orm.dao.classes;

import com.web.services.orm.dao.interfaces.PurchaseDAO;
import com.web.services.orm.entity.login.UserAccount;
import com.web.services.orm.entity.transaction.Purchase;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class PurchaseDAOImpl extends CRUD<Purchase> implements PurchaseDAO {

    public PurchaseDAOImpl() {
        super(Purchase.class);
    }

    @Override
    public List<Purchase> getUnchecked(UserAccount userAccount) {
        try {
            Query query = super.entityManager.createQuery(String.format(
                    "FROM %s WHERE checkout IS NULL AND userAccount = :value",
                    Purchase.class.getSimpleName()))
                    .setParameter("value", userAccount);

            return query.getResultList();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Purchase> getChecked() {
        try {
            Query query = super.entityManager.createQuery(String.format(
                    "FROM %s WHERE checkout IS NOT NULL", Purchase.class.getSimpleName()));

            return query.getResultList();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
