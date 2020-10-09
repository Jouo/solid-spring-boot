package com.web.services.utility.orm.classes;

import com.web.services.orm.api.GroupAPI;
import com.web.services.orm.api.SaleAPI;
import com.web.services.orm.dao.interfaces.ProductDAO;
import com.web.services.orm.entity.business.Product;
import com.web.services.orm.entity.transaction.Order;
import com.web.services.orm.entity.transaction.Sale;
import com.web.services.utility.orm.interfaces.OrderUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderUtilsImpl implements OrderUtils {

    private ProductDAO productDAO;

    public OrderUtilsImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public Float getTotal(List<Order> orders) {
        float total = 0.0f;
        for (Order order : orders) {
            total += order.getTotal();
        }
        return total;
    }

    @Override
    public void setSale(List<Order> orders, Sale sale) {
        for (Order order : orders) {
            order.setSale(sale);
        }
    }

    @Override
    public List<Order> getOrders(SaleAPI api) {
        List<Order> orders = new ArrayList<>();

        for (GroupAPI group : api.getProducts()) {
            Order order = getOrder(group);
            if (order != null) {
                orders.add(order);
            }
        }
        return orders;
    }

    private Order getOrder(GroupAPI api) {
        Product product = productDAO.find(api.getId());
        if (product != null) {
            int quantity = api.getQuantity();
            float total = product.getPrice() * quantity;
            Order order = new Order();
            order.setQuantity(quantity);
            order.setTotal(total);
            order.setProduct(product);
            return order;
        }
        return null;
    }
}
