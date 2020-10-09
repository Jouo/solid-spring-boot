package com.web.services.utility.orm.interfaces;

import com.web.services.orm.api.SaleAPI;
import com.web.services.orm.entity.transaction.Sale;

import java.util.List;

public interface SaleUtils extends Utils<Sale, SaleAPI> {

    Float getTotal(List<Sale> sales);

    boolean isMatch(Sale sale, SaleAPI api);
}
