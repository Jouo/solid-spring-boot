package com.web.services.rest.controller.mock;

import com.web.services.orm.api.PurchaseAPI;
import com.web.services.orm.entity.transaction.Purchase;
import com.web.services.orm.service.interfaces.PurchaseService;
import com.web.services.rest.controller.PurchaseREST;
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
class PurchaseMockTest {

    @InjectMocks
    private PurchaseREST rest;

    @Mock
    private PurchaseService service;

    @Mock
    private Purchase entity;

    @Mock
    private PurchaseAPI api;

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
    void read() {
        when(service.get(anyLong())).thenReturn(entity);
        when(httpResponse.response(entity)).thenCallRealMethod();
        response = rest.read(anyLong());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).get(anyLong());
    }

    @Test
    @Order(3)
    void readChecked() {
        when(service.getChecked()).thenReturn(new ArrayList<>());
        response = rest.readChecked();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).getChecked();
    }

    @Test
    @Order(4)
    void readUnchecked() {
        when(service.getUnchecked(anyLong())).thenReturn(new ArrayList<>());
        response = rest.readUnchecked(anyLong());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).getUnchecked(anyLong());
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
}