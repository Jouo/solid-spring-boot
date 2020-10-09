package com.web.services.utility.orm.interfaces;

import com.web.services.orm.api.SaleAPI;
import com.web.services.orm.entity.transaction.Order;
import com.web.services.orm.entity.transaction.Sale;

import java.util.List;

public interface OrderUtils {

    Float getTotal(List<Order> orders);

    List<Order> getOrders(SaleAPI api);

    void setSale(List<Order> orders, Sale sale);
}
