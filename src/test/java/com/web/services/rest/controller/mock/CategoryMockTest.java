package com.web.services.rest.controller.mock;

import com.web.services.orm.api.CategoryAPI;
import com.web.services.orm.entity.configuration.Category;
import com.web.services.orm.service.interfaces.CategoryService;
import com.web.services.rest.controller.CategoryREST;
import com.web.services.rest.utility.http.BindingResponseFactory;
import com.web.services.rest.utility.http.HttpResponseFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.BDDMockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class CategoryMockTest {

    @InjectMocks
    private CategoryREST rest;

    @Mock
    private CategoryService service;

    @Mock
    private Category entity;

    @Mock
    private CategoryAPI api;

    @Mock
    private BindingResult binding;

    @Mock
    private BindingResponseFactory bindingResponse;

    @Mock
    private HttpResponseFactory httpResponse;

    private ResponseEntity<?> response;

    @Test
    @Order(1)
    void create() {
        when(binding.hasErrors()).thenReturn(false);
        when(service.save(api)).thenReturn(entity);
        when(httpResponse.response(entity)).thenCallRealMethod();
        response = rest.create(api, binding);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).save(api);
        verifyNoInteractions(bindingResponse);
    }

    @Test
    @Order(2)
    void readList() {
        when(service.get()).thenReturn(new ArrayList<>());
        response = rest.read();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).get();
    }

    @Test
    @Order(3)
    void read() {
        when(service.get(anyLong())).thenReturn(entity);
        when(httpResponse.response(entity)).thenCallRealMethod();
        response = rest.read(anyLong());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).get(anyLong());
    }

    @Test
    @Order(4)
    void update() {
        when(binding.hasErrors()).thenReturn(false);
        when(service.update(0L, api)).thenReturn(true);
        response = rest.update(0L, api, binding);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).update(0L, api);
        verifyNoInteractions(bindingResponse);
    }

    @Test
    @Order(5)
    void createThrow() {
        when(binding.hasErrors()).thenReturn(true);
        when(bindingResponse.response(binding)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        response = rest.create(api, binding);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verifyNoInteractions(service);
    }

    @Test
    @Order(6)
    void readThrow() {
        when(service.get(anyLong())).thenReturn(null);
        when(httpResponse.response(null)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        response = rest.read(anyLong());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(service, times(1)).get(anyLong());
    }

    @Test
    @Order(7)
    void updateThrow() {
        when(binding.hasErrors()).thenReturn(true);
        when(bindingResponse.response(binding)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        response = rest.update(0L, api, binding);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verifyNoInteractions(service);
    }
}