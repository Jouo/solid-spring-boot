package com.web.services.rest.controller.mock;

import com.web.services.orm.api.ConfigurationAPI;
import com.web.services.orm.entity.configuration.Configuration;
import com.web.services.orm.service.interfaces.ConfigurationService;
import com.web.services.rest.controller.ConfigurationREST;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class ConfigurationMockTest {

    @InjectMocks
    private ConfigurationREST rest;

    @Mock
    private ConfigurationService service;

    @Mock
    private Configuration entity;

    @Mock
    private ConfigurationAPI api;

    @Mock
    private BindingResult binding;

    @Mock
    private BindingResponseFactory bindingResponse;

    @Mock
    private HttpResponseFactory httpResponse;

    private ResponseEntity<?> response;

    @Test
    @Order(1)
    void read() {
        when(service.get(anyLong())).thenReturn(entity);
        when(httpResponse.response(entity)).thenCallRealMethod();
        response = rest.read();
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).get(anyLong());
    }

    @Test
    @Order(2)
    void update() {
        when(binding.hasErrors()).thenReturn(false);
        when(service.update(1L, api)).thenReturn(true);
        response = rest.update(api, binding);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).update(1L, api);
        verifyNoInteractions(bindingResponse);
    }

    @Test
    @Order(3)
    void updateThrow() {
        when(binding.hasErrors()).thenReturn(true);
        when(bindingResponse.response(binding)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        response = rest.update(api, binding);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verifyNoInteractions(service);
    }
}