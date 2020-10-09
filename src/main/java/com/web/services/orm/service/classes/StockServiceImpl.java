package com.web.services.orm.service.classes;

import com.web.services.orm.api.StockAPI;
import com.web.services.orm.dao.interfaces.ProductDAO;
import com.web.services.orm.dao.interfaces.StockDAO;
import com.web.services.orm.entity.business.Product;
import com.web.services.orm.entity.business.Stock;
import com.web.services.orm.service.interfaces.StockService;
import com.web.services.utility.orm.interfaces.StockUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class StockServiceImpl
        extends CRUD<Stock, StockAPI, StockDAO, StockUtils>
        implements StockService {

    private ProductDAO productDAO;

    public StockServiceImpl(ProductDAO productDAO, StockDAO stockDAO, StockUtils stockUtils) {
        super(stockDAO, stockUtils);
        this.productDAO = productDAO;
    }

    @Override
    @Transactional
    public boolean update(Long id, StockAPI api) {
        Product product = productDAO.find(id);
        if (product != null) {
            Stock stock = product.getStock();
            boolean updated = super.utils.update(stock, api);

            if (updated) {
                super.dao.merge(stock);
                return true;
            }
        }
        return false;
    }
}
