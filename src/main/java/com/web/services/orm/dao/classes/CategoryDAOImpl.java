package com.web.services.orm.dao.classes;

import com.web.services.orm.dao.interfaces.CategoryDAO;
import com.web.services.orm.entity.configuration.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CategoryDAOImpl extends CRUD<Category> implements CategoryDAO {

    public CategoryDAOImpl() {
        super(Category.class);
    }
}
