package com.web.services.rest.controller.mock;

import com.web.services.orm.api.ProductAPI;
import com.web.services.orm.api.UpdateProductAPI;
import com.web.services.orm.entity.business.Product;
import com.web.services.orm.service.interfaces.ProductService;
import com.web.services.rest.controller.ProductREST;
import com.web.services.rest.utility.http.BindingResponseFactory;
import com.web.services.rest.utility.http.HttpResponseFactory;
import com.web.services.utility.orm.interfaces.ProductUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
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
class ProductMockTest {

    @InjectMocks
    private ProductREST rest;

    @Mock
    private ProductService service;

    @Mock
    private ProductUtils utils;

    @Mock
    private Product entity;

    @Mock
    private ProductAPI newAPI;

    @Mock
    private UpdateProductAPI updateAPI;

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
        when(service.save(newAPI)).thenReturn(entity);
        when(httpResponse.response(entity)).thenCallRealMethod();
        response = rest.create(newAPI, binding);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).save(newAPI);
        verifyNoInteractions(bindingResponse);
    }

    @Test
    @Order(3)
    void readByCategory() {
        when(service.getByCategory(anyLong())).thenReturn(new ArrayList<>());
        response = rest.readByCategory(anyLong());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).getByCategory(anyLong());
    }

    @Test
    @Order(4)
    void readList() {
        when(service.get()).thenReturn(new ArrayList<>());
        response = rest.read();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).get();
    }

    @Test
    @Order(5)
    void read() {
        when(service.get(anyLong())).thenReturn(entity);
        when(httpResponse.response(entity)).thenCallRealMethod();
        response = rest.read(anyLong());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).get(anyLong());
    }

    @Test
    @Order(6)
    void update() {
        when(binding.hasErrors()).thenReturn(false);
        when(service.update(0L, newAPI)).thenReturn(true);
        when(utils.fromUpdate(updateAPI)).thenReturn(newAPI);
        response = rest.update(0L, updateAPI, binding);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).update(0L, newAPI);
        verifyNoInteractions(bindingResponse);
    }

    @Test
    @Order(7)
    void createThrow() {
        when(binding.hasErrors()).thenReturn(true);
        when(bindingResponse.response(binding)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        response = rest.create(newAPI, binding);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verifyNoInteractions(service);
    }

    @Test
    @Order(8)
    void readThrow() {
        when(service.get(anyLong())).thenReturn(null);
        when(httpResponse.response(null)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        response = rest.read(anyLong());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(service, times(1)).get(anyLong());
    }

    @Test
    @Order(9)
    void updateThrow() {
        when(binding.hasErrors()).thenReturn(true);
        when(bindingResponse.response(binding)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        response = rest.update(0L, updateAPI, binding);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verifyNoInteractions(service);
    }
}