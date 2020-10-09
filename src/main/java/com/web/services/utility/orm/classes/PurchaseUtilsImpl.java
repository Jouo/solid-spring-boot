package com.web.services.utility.orm.classes;

import com.web.services.orm.api.GroupAPI;
import com.web.services.orm.api.PurchaseAPI;
import com.web.services.orm.dao.interfaces.ProviderDAO;
import com.web.services.orm.entity.business.ProductGroup;
import com.web.services.orm.entity.configuration.Provider;
import com.web.services.orm.entity.login.UserAccount;
import com.web.services.orm.entity.transaction.Purchase;
import com.web.services.utility.orm.interfaces.ProductGroupUtils;
import com.web.services.utility.orm.interfaces.PurchaseUtils;
import com.web.services.utility.orm.interfaces.UserUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PurchaseUtilsImpl implements PurchaseUtils {

    private ProviderDAO providerDAO;
    private UserUtils userUtils;
    private ProductGroupUtils productGroupUtils;

    public PurchaseUtilsImpl(ProviderDAO providerDAO, UserUtils userUtils, ProductGroupUtils productGroupUtils) {
        this.providerDAO = providerDAO;
        this.userUtils = userUtils;
        this.productGroupUtils = productGroupUtils;
    }

    @Override
    public Purchase convertAPI(PurchaseAPI api) {
        UserAccount userAccount = userUtils.getUserAccount();
        Provider provider = providerDAO.find(api.getProvider());
        if ((userAccount != null) && (provider != null)) {
            Purchase purchase = new Purchase();
            setPurchase(purchase, api);
            purchase.setProvider(provider);
            purchase.setUserAccount(userAccount);
            return purchase;
        }
        return null;
    }

    @Override
    public boolean update(Purchase purchase, PurchaseAPI api) {
        Provider provider = providerDAO.find(api.getProvider());
        if (provider != null) {
            purchase.setProvider(provider);
            setPurchase(purchase, api);
            return true;
        }
        return false;
    }

    @Override
    public Float getTotal(List<Purchase> purchases) {
        Float total = 0.0f;
        for (Purchase purchase : purchases) {
            total += purchase.getTotal();
        }
        return total;
    }

    @Override
    public boolean isMatch(Purchase purchase, PurchaseAPI api) {
        Long idProvider = purchase.getProvider().getId();
        if (idProvider != api.getProvider()) { return false; }

        for (ProductGroup group : purchase.getProducts()) {
            if (!groupMatch(group, api.getProducts())) { return false; }
        }
        return true;
    }

    private boolean groupMatch(ProductGroup group, List<GroupAPI> apis) {
        Long idProduct = group.getProduct().getId();

        for (GroupAPI api : apis) {
            if (idProduct == api.getId()
                    && group.getQuantity() == api.getQuantity()) {
                return true;
            }
        }
        return false;
    }

    private void setPurchase(Purchase purchase, PurchaseAPI api) {
        List<ProductGroup> productGroups = productGroupUtils.getProductGroups(api);
        float total = 0.0f;
        for (ProductGroup productGroup : productGroups) {
            float cost = productGroup.getProduct().getCost();
            int quantity = productGroup.getQuantity();
            total += cost * quantity;
        }
        purchase.setProducts(productGroups);
        purchase.setTotal(total);
    }
}
