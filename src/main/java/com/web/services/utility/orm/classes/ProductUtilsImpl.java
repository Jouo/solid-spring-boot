package com.web.services.utility.orm.classes;

import com.web.services.orm.api.ProductAPI;
import com.web.services.orm.api.UpdateProductAPI;
import com.web.services.orm.dao.interfaces.CategoryDAO;
import com.web.services.orm.entity.business.Product;
import com.web.services.orm.entity.business.Stock;
import com.web.services.orm.entity.configuration.Category;
import com.web.services.utility.orm.interfaces.ProductUtils;
import com.web.services.utility.orm.interfaces.StockUtils;
import org.springframework.stereotype.Component;

@Component
public class ProductUtilsImpl implements ProductUtils {

    private CategoryDAO categoryDAO;
    private StockUtils stockUtils;

    public ProductUtilsImpl(CategoryDAO categoryDAO, StockUtils stockUtils) {
        this.categoryDAO = categoryDAO;
        this.stockUtils = stockUtils;
    }

    @Override
    public Product convertAPI(ProductAPI api) {
        Product product = new Product();
        boolean updated = setProduct(product, api);
        if (updated) {
            Stock stock = stockUtils.convertAPI(api.getStock());
            product.setStock(stock);
            return product;
        }
        return null;
    }

    @Override
    public boolean update(Product product, ProductAPI api) {
        return setProduct(product, api);
    }

    @Override
    public ProductAPI fromUpdate(UpdateProductAPI update) {
        ProductAPI api = new ProductAPI();
        api.setName(update.getName());
        api.setCost(update.getCost());
        api.setPrice(update.getPrice());
        api.setImage(update.getImage());
        api.setCategory(update.getCategory());
        return api;
    }

    private boolean setProduct(Product product, ProductAPI api) {
        Category category = categoryDAO.find(api.getCategory());
        if (category != null) {
            product.setCategory(category);
            product.setName(api.getName());
            product.setCost(api.getCost());
            product.setPrice(api.getPrice());
            product.setImage(api.getImage());
            return true;
        }
        return false;
    }
}
