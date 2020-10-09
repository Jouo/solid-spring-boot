package com.web.services.utility.orm.classes;

import com.web.services.orm.api.GroupAPI;
import com.web.services.orm.api.PurchaseAPI;
import com.web.services.orm.dao.interfaces.ProductDAO;
import com.web.services.orm.entity.business.Product;
import com.web.services.orm.entity.business.ProductGroup;
import com.web.services.utility.orm.interfaces.ProductGroupUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductGroupUtilsImpl implements ProductGroupUtils {

    private ProductDAO productDAO;

    public ProductGroupUtilsImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public List<ProductGroup> getProductGroups(PurchaseAPI api) {
        List<ProductGroup> productGroups = new ArrayList<>();

        for (GroupAPI groupAPI : api.getProducts()) {
            Product product = productDAO.find(groupAPI.getId());

            if (product != null) {
                int quantity = groupAPI.getQuantity();
                ProductGroup productGroup = new ProductGroup(quantity, product);
                productGroups.add(productGroup);
            }
        }
        return productGroups;
    }
}
