package com.web.services.orm.dao.classes;

import com.web.services.orm.dao.interfaces.SaleDAO;
import com.web.services.orm.entity.login.UserAccount;
import com.web.services.orm.entity.transaction.Sale;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class SaleDAOImpl extends CRUD<Sale> implements SaleDAO {

    public SaleDAOImpl() {
        super(Sale.class);
    }

    @Override
    public List<Sale> getUnchecked(UserAccount userAccount) {
        try {
            Query query = super.entityManager.createQuery(String.format(
                    "FROM %s WHERE checkout IS NULL AND userAccount = :value",
                    Sale.class.getSimpleName()))
                    .setParameter("value", userAccount);

            return query.getResultList();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Sale> getChecked() {
        try {
            Query query = super.entityManager.createQuery(String.format(
                    "FROM %s WHERE checkout IS NOT NULL",
                    Sale.class.getSimpleName()));

            return query.getResultList();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
