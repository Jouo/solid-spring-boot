package com.web.services.rest.controller.mock;

import com.web.services.orm.api.StockAPI;
import com.web.services.orm.service.interfaces.StockService;
import com.web.services.rest.controller.StockREST;
import com.web.services.rest.utility.http.BindingResponseFactory;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class StockMockTest {

    @InjectMocks
    private StockREST rest;

    @Mock
    private StockService service;

    @Mock
    private StockAPI api;

    @Mock
    private BindingResult binding;

    @Mock
    private BindingResponseFactory bindingResponse;

    private ResponseEntity<?> response;

    @Test
    @Order(1)
    void update() {
        when(binding.hasErrors()).thenReturn(false);
        when(service.update(0L, api)).thenReturn(true);
        response = rest.update(0L, api, binding);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).update(0L, api);
        verifyNoInteractions(bindingResponse);
    }

    @Test
    @Order(2)
    void updateThrow() {
        when(binding.hasErrors()).thenReturn(true);
        when(bindingResponse.response(binding)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        response = rest.update(0L, api, binding);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verifyNoInteractions(service);
    }
}