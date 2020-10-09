package com.web.services.orm.dao.interfaces;

import com.web.services.orm.entity.business.Product;
import com.web.services.orm.entity.configuration.Category;

import java.util.List;

public interface ProductDAO extends DAO<Product> {

    List<Product> find(Category category);
}
