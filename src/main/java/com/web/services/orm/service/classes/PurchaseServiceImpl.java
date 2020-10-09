package com.web.services.orm.service.classes;

import com.web.services.orm.api.PurchaseAPI;
import com.web.services.orm.dao.interfaces.PurchaseDAO;
import com.web.services.orm.dao.interfaces.StockDAO;
import com.web.services.orm.dao.interfaces.UserAccountDAO;
import com.web.services.orm.entity.business.ProductGroup;
import com.web.services.orm.entity.login.UserAccount;
import com.web.services.orm.entity.transaction.Purchase;
import com.web.services.orm.service.interfaces.PurchaseService;
import com.web.services.utility.orm.interfaces.PurchaseUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseServiceImpl
        extends CRUD<Purchase, PurchaseAPI, PurchaseDAO, PurchaseUtils>
        implements PurchaseService {

    private UserAccountDAO userAccountDAO;
    private StockDAO stockDAO;

    public PurchaseServiceImpl(UserAccountDAO userAccountDAO, PurchaseDAO purchaseDAO,
                               PurchaseUtils purchaseUtils, StockDAO stockDAO) {
        super(purchaseDAO, purchaseUtils);
        this.userAccountDAO = userAccountDAO;
        this.stockDAO = stockDAO;
    }

    @Override
    @Transactional
    public List<Purchase> getUnchecked(Long idUser) {
        UserAccount userAccount = userAccountDAO.find(idUser);
        if (userAccount != null) {
            return super.dao.getUnchecked(userAccount);
        }
        return new ArrayList<>();
    }

    @Override
    @Transactional
    public List<Purchase> getChecked() {
        return super.dao.getChecked();
    }

    @Override
    @Transactional
    public Purchase save(PurchaseAPI api) {
        Purchase purchase = super.utils.convertAPI(api);
        if (purchase != null) {
            for (ProductGroup group : purchase.getProducts()) {
                stockDAO.increase(group);
            }
            return super.dao.persist(purchase);
        }
        return null;
    }
}
