package com.web.services.orm.dao.interfaces;

import java.util.List;

public interface DAO<T> {

    T persist(T object);

    T find(Long id);

    List<T> find();

    T merge(T object);

    void delete(Long id);

    void delete(T object);

    void refresh(T object);
}
