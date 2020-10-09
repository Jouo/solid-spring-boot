package com.web.services.orm.service.classes;

import com.web.services.orm.dao.interfaces.CheckoutDAO;
import com.web.services.orm.dao.interfaces.PurchaseDAO;
import com.web.services.orm.dao.interfaces.SaleDAO;
import com.web.services.orm.dao.interfaces.UserAccountDAO;
import com.web.services.orm.entity.login.UserAccount;
import com.web.services.orm.entity.transaction.Checkout;
import com.web.services.orm.entity.transaction.Purchase;
import com.web.services.orm.entity.transaction.Sale;
import com.web.services.orm.service.interfaces.CheckoutService;
import com.web.services.utility.orm.interfaces.PurchaseUtils;
import com.web.services.utility.orm.interfaces.SaleUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private UserAccountDAO userAccountDAO;
    private SaleDAO saleDAO;
    private PurchaseDAO purchaseDAO;
    private CheckoutDAO checkoutDAO;
    private SaleUtils saleUtils;
    private PurchaseUtils purchaseUtils;

    public CheckoutServiceImpl(UserAccountDAO userAccountDAO, SaleDAO saleDAO, PurchaseDAO purchaseDAO,
                               CheckoutDAO checkoutDAO, SaleUtils saleUtils, PurchaseUtils purchaseUtils) {
        this.userAccountDAO = userAccountDAO;
        this.saleDAO = saleDAO;
        this.purchaseDAO = purchaseDAO;
        this.checkoutDAO = checkoutDAO;
        this.saleUtils = saleUtils;
        this.purchaseUtils = purchaseUtils;
    }

    @Override
    @Transactional
    public Checkout generate(Long idUser) {
        UserAccount userAccount = userAccountDAO.find(idUser);
        if (userAccount != null) {
            List<Sale> sales = saleDAO.getUnchecked(userAccount);
            List<Purchase> purchases = purchaseDAO.getUnchecked(userAccount);
            Checkout checkout = new Checkout(userAccount);
            checkout.setSales(sales);
            checkout.setPurchases(purchases);
            checkout.setTotalSale(saleUtils.getTotal(sales));
            checkout.setTotalPurchase(purchaseUtils.getTotal(purchases));
            return checkoutDAO.persist(checkout);
        }
        return null;
    }

    @Override
    @Transactional
    public List<Checkout> getByUser(Long idUser) {
        UserAccount userAccount = userAccountDAO.find(idUser);
        if (userAccount != null) {
            return checkoutDAO.getByUser(userAccount);
        }
        return new ArrayList<>();
    }

    @Override
    @Transactional
    public List<Checkout> get() {
        return checkoutDAO.find();
    }

    @Override
    @Transactional
    public Checkout get(Long id) {
        return checkoutDAO.find(id);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Checkout checkout = checkoutDAO.find(id);
        if (checkout != null) {
            checkoutDAO.delete(checkout);
            return true;
        }
        return false;
    }
}
