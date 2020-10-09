package com.web.services.orm.dao.interfaces;

import com.web.services.orm.entity.business.ProductGroup;
import com.web.services.orm.entity.business.Stock;
import com.web.services.orm.entity.transaction.Order;

public interface StockDAO extends DAO<Stock> {

    void increase(ProductGroup productGroup);

    void decrease(Order order);
}
