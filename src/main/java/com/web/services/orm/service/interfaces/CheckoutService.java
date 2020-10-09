package com.web.services.orm.service.interfaces;

import com.web.services.orm.entity.transaction.Checkout;

import java.util.List;

public interface CheckoutService {

    Checkout generate(Long idUser);

    List<Checkout> getByUser(Long idUser);

    List<Checkout> get();

    Checkout get(Long id);

    boolean delete(Long id);
}
