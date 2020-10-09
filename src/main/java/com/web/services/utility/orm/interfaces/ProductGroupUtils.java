package com.web.services.utility.orm.interfaces;

import com.web.services.orm.api.PurchaseAPI;
import com.web.services.orm.entity.business.ProductGroup;

import java.util.List;

public interface ProductGroupUtils {

    List<ProductGroup> getProductGroups(PurchaseAPI api);
}
