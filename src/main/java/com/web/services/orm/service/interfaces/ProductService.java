package com.web.services.orm.service.interfaces;

import com.web.services.orm.api.ProductAPI;
import com.web.services.orm.entity.business.Product;

import java.util.List;

public interface ProductService extends Service<Product, ProductAPI> {

    List<Product> getByCategory(Long id);
}
