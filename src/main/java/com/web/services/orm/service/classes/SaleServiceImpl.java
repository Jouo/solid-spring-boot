package com.web.services.orm.service.classes;

import com.web.services.orm.api.SaleAPI;
import com.web.services.orm.dao.interfaces.SaleDAO;
import com.web.services.orm.dao.interfaces.StockDAO;
import com.web.services.orm.dao.interfaces.UserAccountDAO;
import com.web.services.orm.entity.login.UserAccount;
import com.web.services.orm.entity.transaction.Order;
import com.web.services.orm.entity.transaction.Sale;
import com.web.services.orm.service.interfaces.SaleService;
import com.web.services.utility.orm.interfaces.SaleUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleServiceImpl
    extends CRUD<Sale, SaleAPI, SaleDAO, SaleUtils>
        implements SaleService {

    private UserAccountDAO userAccountDAO;
    private StockDAO stockDAO;

    public SaleServiceImpl(UserAccountDAO userAccountDAO, StockDAO stockDAO,
                           SaleDAO saleDAO, SaleUtils saleUtils) {
        super(saleDAO, saleUtils);
        this.userAccountDAO = userAccountDAO;
        this.stockDAO = stockDAO;
    }

    @Override
    @Transactional
    public List<Sale> getUnchecked(Long idUser) {
        UserAccount userAccount = userAccountDAO.find(idUser);
        if (userAccount != null) {
            return super.dao.getUnchecked(userAccount);
        }
        return new ArrayList<>();
    }

    @Override
    @Transactional
    public List<Sale> getChecked() {
        return super.dao.getChecked();
    }

    @Override
    @Transactional
    public Sale save(SaleAPI api) {
        Sale sale = super.utils.convertAPI(api);
        if (sale != null) {
            for (Order order : sale.getOrders()) {
                stockDAO.decrease(order);
            }
            return super.dao.persist(sale);
        }
        return null;
    }
}
