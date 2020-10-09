package com.web.services.orm.dao.interfaces;

public interface APIBinderDAO {

    boolean exists(String table, Long id);

    boolean exists(String table, String column, Object value);
}
