package com.web.services.orm.dao.classes;

import com.web.services.orm.dao.interfaces.APIBinderDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
public class APIBinderDAOImpl implements APIBinderDAO {

    private EntityManager entityManager;

    public APIBinderDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean exists(String table, Long id) {
        Query query = this.entityManager.createQuery(String.format(
                "FROM %s WHERE id = :id", table))
                .setParameter("id", id);

        return !query.getResultList().isEmpty();
    }

    @Override
    public boolean exists(String table, String column, Object value) {
        Query query = this.entityManager.createQuery(String.format(
                "FROM %s WHERE %s = :value", table, column))
                .setParameter("value", value);

        return !query.getResultList().isEmpty();
    }
}
