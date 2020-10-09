package com.web.services.orm.service.interfaces;

import com.web.services.orm.api.SaleAPI;
import com.web.services.orm.entity.transaction.Sale;

import java.util.List;

public interface SaleService extends Service<Sale, SaleAPI> {

    List<Sale> getUnchecked(Long idUser);

    List<Sale> getChecked();
}
