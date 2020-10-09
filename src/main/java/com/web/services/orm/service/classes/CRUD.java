package com.web.services.orm.service.classes;

import com.web.services.orm.dao.interfaces.DAO;
import com.web.services.utility.orm.interfaces.Utils;

import javax.transaction.Transactional;
import java.util.List;

public class CRUD<T, A, D extends DAO<T>, U extends Utils<T, A>> {

    protected D dao;
    protected U utils;

    public CRUD(D dao, U utils) {
        this.dao = dao;
        this.utils = utils;
    }

    @Transactional
    public List<T> get() {
        return dao.find();
    }

    @Transactional
    public T save(A api) {
        T entity = utils.convertAPI(api);
        if (entity != null) {
            dao.persist(entity);
            return entity;
        }
        return null;
    }

    @Transactional
    public T get(Long id) {
        return dao.find(id);
    }

    @Transactional
    public boolean update(Long id, A api) {
        T entity = dao.find(id);
        if (entity != null) {
            boolean updated = utils.update(entity, api);

            if (updated) {
                dao.merge(entity);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean delete(Long id) {
        T entity = dao.find(id);
        if (entity != null) {
            dao.delete(entity);
            return true;
        }
        return false;
    }

    @Transactional
    public void refresh(T object) {
        dao.refresh(object);
    }
}
