package com.web.services.orm.dao.classes;

import com.web.services.orm.dao.interfaces.ProductGroupDAO;
import com.web.services.orm.entity.business.ProductGroup;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ProductGroupDAOImpl extends CRUD<ProductGroup> implements ProductGroupDAO {

    public ProductGroupDAOImpl() {
        super(ProductGroup.class);
    }
}
