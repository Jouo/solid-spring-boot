package com.web.services.utility.orm.interfaces;

import com.web.services.orm.api.ProductAPI;
import com.web.services.orm.api.UpdateProductAPI;
import com.web.services.orm.entity.business.Product;

public interface ProductUtils extends Utils<Product, ProductAPI> {

    ProductAPI fromUpdate(UpdateProductAPI update);
}
