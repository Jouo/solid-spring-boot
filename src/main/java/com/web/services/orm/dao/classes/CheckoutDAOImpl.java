package com.web.services.orm.dao.classes;

import com.web.services.orm.dao.interfaces.CheckoutDAO;
import com.web.services.orm.entity.login.UserAccount;
import com.web.services.orm.entity.transaction.Checkout;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CheckoutDAOImpl extends CRUD<Checkout> implements CheckoutDAO {

    public CheckoutDAOImpl() {
        super(Checkout.class);
    }

    @Override
    public List<Checkout> getByUser(UserAccount userAccount) {
        try {
            Query query = super.entityManager.createQuery(String.format(
                    "FROM %s WHERE userAccount = :user",
                    Checkout.class.getSimpleName()))
                    .setParameter("user", userAccount);

            return query.getResultList();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
