package com.web.services.orm.dao.classes;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class CRUD<T> {

    private final Class entityClass;
    private final String className;

    @Autowired
    protected EntityManager entityManager;

    public CRUD(Class entityClass) {
        this.entityClass = entityClass;
        this.className = entityClass.getSimpleName();
    }

    public T persist(T object) {
        entityManager.persist(object);
        return object;
    }

    public List<T> find() {
        String command = String.format("FROM %s", className);
        Query query = entityManager.createQuery(command);

        return query.getResultList();
    }

    public T find(Long id) {
        return (T) entityManager.find(this.entityClass, id);
    }

    public T merge(T object) {
        entityManager.merge(object);
        return object;
    }

    public void delete(T object) {
        entityManager.remove(object);
    }

    public void delete(Long id) {
        String command = String.format("DELETE FROM %s WHERE id = :id", className);
        Query query = entityManager.createQuery(command)
                .setParameter("id", id);

        query.executeUpdate();
    }

    public void refresh(T object) {
        entityManager.refresh(object);
    }
}
