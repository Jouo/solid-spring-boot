package com.web.services.utility.orm.interfaces;

import com.web.services.orm.api.PurchaseAPI;
import com.web.services.orm.entity.transaction.Purchase;

import java.util.List;

public interface PurchaseUtils extends Utils<Purchase, PurchaseAPI> {

    Float getTotal(List<Purchase> purchases);

    boolean isMatch(Purchase purchase, PurchaseAPI api);
}
