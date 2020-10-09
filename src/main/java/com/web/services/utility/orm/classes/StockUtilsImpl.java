package com.web.services.utility.orm.classes;

import com.web.services.orm.api.StockAPI;
import com.web.services.orm.entity.business.Stock;
import com.web.services.utility.orm.interfaces.StockUtils;
import org.springframework.stereotype.Component;

@Component
public class StockUtilsImpl implements StockUtils {

    @Override
    public Stock convertAPI(StockAPI api) {
        Stock stock = new Stock();
        setStock(stock, api);
        return stock;
    }

    @Override
    public boolean update(Stock stock, StockAPI api) {
        setStock(stock, api);
        return true;
    }

    void setStock(Stock stock, StockAPI api) {
        stock.setQuantity(api.getQuantity());
    }
}
