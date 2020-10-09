package com.web.services.orm.service.classes;

import com.web.services.orm.api.ProductAPI;
import com.web.services.orm.dao.interfaces.CategoryDAO;
import com.web.services.orm.dao.interfaces.ProductDAO;
import com.web.services.orm.entity.business.Product;
import com.web.services.orm.entity.configuration.Category;
import com.web.services.orm.service.interfaces.ProductService;
import com.web.services.utility.orm.interfaces.ProductUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl
        extends CRUD<Product, ProductAPI, ProductDAO, ProductUtils>
        implements ProductService {

    private CategoryDAO categoryDAO;

    public ProductServiceImpl(ProductDAO productDAO, ProductUtils productUtils, CategoryDAO categoryDAO) {
        super(productDAO, productUtils);
        this.categoryDAO = categoryDAO;
    }

    @Override
    @Transactional
    public List<Product> getByCategory(Long idCategory) {
        Category category = categoryDAO.find(idCategory);
        if (category != null) {
            return super.dao.find(category);
        }
        return new ArrayList<>();
    }
}
