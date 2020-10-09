package com.web.services.orm.dao.classes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SuppressWarnings(value = "unchecked")
@ExtendWith(MockitoExtension.class)
class DAOMockTest<T> {

    @InjectMocks
    private CRUD dao = new CRUD(Object.class);

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @Mock
    private Object object;

    @Test
    void persist() {
        T entity = (T) dao.persist(object);
        assertNotNull(entity);
        verify(entityManager, times(1)).persist(object);
    }

    @Test
    void findList() {
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList());
        List<T> list = dao.find();
        assertNotNull(list);
        verify(entityManager, times(1)).createQuery(anyString());
        verify(query, times(1)).getResultList();
    }

    @Test
    void find() {
        when(entityManager.find(any(), eq(0L))).thenReturn(object);
        T entity = (T) dao.find(0L);
        assertNotNull(entity);
        verify(entityManager, times(1)).find(any(), eq(0L));
    }

    @Test
    void merge() {
        dao.merge(object);
        verify(entityManager, times(1)).merge(object);
    }

    @Test
    void deleteObject() {
        dao.delete(object);
        verify(entityManager, times(1)).remove(object);
    }

    @Test
    void deleteByID() {
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        dao.delete(anyLong());
        verify(query, times(1)).executeUpdate();
    }

    @Test
    void refresh() {
        dao.refresh(object);
        verify(entityManager, times(1)).refresh(object);
    }
}