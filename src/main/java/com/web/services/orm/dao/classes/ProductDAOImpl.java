package com.web.services.orm.dao.classes;

import com.web.services.orm.dao.interfaces.ProductDAO;
import com.web.services.orm.entity.business.Product;
import com.web.services.orm.entity.configuration.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ProductDAOImpl extends CRUD<Product> implements ProductDAO {

    public ProductDAOImpl() {
        super(Product.class);
    }

    @Override
    public List<Product> find(Category category) {
        try {
            Query query = super.entityManager.createQuery(String.format(
                    "FROM %s WHERE category = :category",
                    Product.class.getSimpleName()))
                    .setParameter("category", category);

            return query.getResultList();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
