package com.web.services.orm.service.interfaces;

import com.web.services.orm.api.PurchaseAPI;
import com.web.services.orm.entity.transaction.Purchase;

import java.util.List;

public interface PurchaseService extends Service<Purchase, PurchaseAPI> {

    List<Purchase> getUnchecked(Long idUser);

    List<Purchase> getChecked();
}
