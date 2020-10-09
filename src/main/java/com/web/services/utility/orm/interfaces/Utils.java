package com.web.services.utility.orm.interfaces;

public interface Utils<T, E> {

    T convertAPI(E api);

    boolean update(T entity, E api);
}
