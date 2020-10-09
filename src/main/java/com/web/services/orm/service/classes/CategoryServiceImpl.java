package com.web.services.orm.service.classes;

import com.web.services.orm.api.CategoryAPI;
import com.web.services.orm.dao.interfaces.CategoryDAO;
import com.web.services.orm.entity.configuration.Category;
import com.web.services.orm.service.interfaces.CategoryService;
import com.web.services.utility.orm.interfaces.CategoryUtils;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl
        extends CRUD<Category, CategoryAPI, CategoryDAO, CategoryUtils>
        implements CategoryService {

    public CategoryServiceImpl(CategoryDAO categoryDAO, CategoryUtils categoryUtils) {
        super(categoryDAO, categoryUtils);
    }
}
