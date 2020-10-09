package com.web.services.orm.service.classes;

import com.web.services.orm.dao.interfaces.DAO;
import com.web.services.utility.orm.interfaces.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SuppressWarnings(value = "unchecked")
@ExtendWith(MockitoExtension.class)
class ServiceMockTest<T, A, D extends DAO<T>, U extends Utils<T, A>> {

    @InjectMocks
    private CRUD service;

    @Mock
    private D dao;

    @Mock
    private T entity;

    @Mock
    private A api;

    @Mock
    private U utils;

    @Test
    void getList() {
        when(dao.find()).thenReturn(new ArrayList<>());
        List<T> list = dao.find();
        assertNotNull(list);
        verify(dao, times(1)).find();
    }

    @Test
    void saveReturn() {
        when(utils.convertAPI(api)).thenReturn(entity);
        when(dao.persist(entity)).thenReturn(entity);
        T entity = (T) service.save(api);
        assertNotNull(entity);
        verify(utils, times(1)).convertAPI(api);
    }

    @Test
    void saveNull() {
        when(utils.convertAPI(api)).thenReturn(isNull());
        T entity = (T) service.save(api);
        assertNull(entity);
        verify(utils, times(1)).convertAPI(api);
        verifyNoInteractions(dao);
    }

    @Test
    void get() {
        when(dao.find(anyLong())).thenReturn(entity);
        T entity = (T) service.get(anyLong());
        assertNotNull(entity);
        verify(dao, times(1)).find(anyLong());
    }

    @Test
    void updateTrue() {
        when(dao.find(anyLong())).thenReturn(entity);
        when(utils.update(entity, api)).thenReturn(true);
        boolean answer = service.update(anyLong(), api);
        assertTrue(answer);
        verify(dao, times(1)).find(anyLong());
        verify(utils, times(1)).update(entity, api);
        verify(dao, times(1)).merge(entity);
    }

    @Test
    void updateFalse() {
        when(dao.find(0L)).thenReturn(isNull());
        boolean answer = service.update(0L, api);
        assertFalse(answer);
        verify(dao, times(1)).find(anyLong());
        verifyNoInteractions(utils);
        verify(dao, times(0)).merge(entity);
    }

    @Test
    void deleteTrue() {
        when(dao.find(anyLong())).thenReturn(entity);
        boolean executed = service.delete(anyLong());
        assertTrue(executed);
        verify(dao, times(1)).find(anyLong());
        verify(dao, times(1)).delete(entity);
    }

    @Test
    void deleteFalse() {
        when(dao.find(0L)).thenReturn(isNull());
        boolean executed = service.delete(0L);
        assertFalse(executed);
        verify(dao, times(1)).find(anyLong());
        verify(dao, times(0)).delete(entity);
    }

    @Test
    void refresh() {
        service.refresh(entity);
        verify(dao, times(1)).refresh(entity);
    }
}