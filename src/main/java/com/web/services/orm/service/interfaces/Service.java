package com.web.services.orm.service.interfaces;

import java.util.List;

public interface Service<T, A> {

    List<T> get();
    T save(A object);
    T get(Long id);
    boolean update(Long id, A object);
    boolean delete(Long id);
    void refresh(T object);
}
