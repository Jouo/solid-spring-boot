package com.web.services.utility.orm.classes;

import com.web.services.orm.api.GroupAPI;
import com.web.services.orm.api.SaleAPI;
import com.web.services.orm.dao.interfaces.CashRegisterDAO;
import com.web.services.orm.entity.business.Product;
import com.web.services.orm.entity.configuration.CashRegister;
import com.web.services.orm.entity.login.UserAccount;
import com.web.services.orm.entity.transaction.Order;
import com.web.services.orm.entity.transaction.Sale;
import com.web.services.utility.orm.interfaces.OrderUtils;
import com.web.services.utility.orm.interfaces.SaleUtils;
import com.web.services.utility.orm.interfaces.UserUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SaleUtilsImpl implements SaleUtils {

    private UserUtils userUtils;
    private OrderUtils orderUtils;
    private CashRegisterDAO cashRegisterDAO;

    public SaleUtilsImpl(UserUtils userUtils, OrderUtils orderUtils, CashRegisterDAO cashRegisterDAO) {
        this.userUtils = userUtils;
        this.orderUtils = orderUtils;
        this.cashRegisterDAO = cashRegisterDAO;
    }

    @Override
    public Sale convertAPI(SaleAPI api) {
        UserAccount userAccount = userUtils.getUserAccount();

        if (userAccount != null) {
            Sale sale = new Sale();
            sale.setUserAccount(userAccount);
            boolean updated = setSale(sale, api);

            if (updated) {
                return sale;
            }
        }
        return null;
    }

    @Override
    public boolean update(Sale sale, SaleAPI api) {
        return setSale(sale, api);
    }

    @Override
    public Float getTotal(List<Sale> sales) {
        Float total = 0.0f;
        for (Sale sale : sales) {
            total += sale.getTotal();
        }
        return total;
    }

    @Override
    public boolean isMatch(Sale sale, SaleAPI api) {
        Long idCashRegister = sale.getCashRegister().getId();
        if (idCashRegister != api.getCashRegister()) { return false; }

        for (Order order : sale.getOrders()) {
            if (!orderMatch(order, api.getProducts())) { return false; }
        }
        return true;
    }

    private boolean orderMatch(Order order, List<GroupAPI> group) {
        Product product = order.getProduct();

        for (GroupAPI api : group) {
            if (api.getId() == product.getId()
                    && api.getQuantity() == order.getQuantity()) {
                return true;
            }
        }
        return false;
    }

    private boolean setSale(Sale sale, SaleAPI api) {
        CashRegister cashRegister = cashRegisterDAO.find(api.getCashRegister());
        if (cashRegister != null) {
            sale.setCashRegister(cashRegister);
            List<Order> orders = orderUtils.getOrders(api);
            orderUtils.setSale(orders, sale);
            float total = orderUtils.getTotal(orders);
            sale.setOrders(orders);
            sale.setTotal(total);
            return true;
        }
        return false;
    }
}
