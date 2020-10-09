package com.web.services.orm.dao.classes;

import com.web.services.orm.dao.interfaces.StockDAO;
import com.web.services.orm.entity.business.ProductGroup;
import com.web.services.orm.entity.business.Stock;
import com.web.services.orm.entity.transaction.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
public class StockDAOImpl extends CRUD<Stock> implements StockDAO {

    public StockDAOImpl() {
        super(Stock.class);
    }

    @Override
    public void increase(ProductGroup productGroup) {
        int currentQuantity = productGroup.getProduct().getStock().getQuantity();
        int updatedQuantity = currentQuantity + productGroup.getQuantity();
        Long idStock = productGroup.getProduct().getStock().getId();

        try {
            Query query = super.entityManager.createQuery(String.format(
                    "UPDATE %s SET quantity = :quantity WHERE id = :id",
                    Stock.class.getSimpleName()))
                    .setParameter("quantity", updatedQuantity)
                    .setParameter("id", idStock);

            query.executeUpdate();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void decrease(Order order) {
        int currentQuantity = order.getProduct().getStock().getQuantity();
        int updatedQuantity = currentQuantity - order.getQuantity();
        Long idStock = order.getProduct().getStock().getId();

        try {
            Query query = super.entityManager.createQuery(String.format(
                    "UPDATE %s SET quantity = :quantity WHERE id = :id",
                    Stock.class.getSimpleName()))
                    .setParameter("quantity", updatedQuantity)
                    .setParameter("id", idStock);

            query.executeUpdate();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
