package com.web.services.rest.controller.mock;

import com.web.services.orm.entity.login.AccountRole;
import com.web.services.orm.service.interfaces.AccountRoleService;
import com.web.services.rest.controller.RoleREST;
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

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class RoleMockTest {

    @InjectMocks
    private RoleREST rest;

    @Mock
    private AccountRoleService service;

    @Mock
    private AccountRole entity;

    @Mock
    private HttpResponseFactory httpResponse;

    private ResponseEntity<?> response;

    @Test
    @Order(1)
    void readList() {
        when(service.get()).thenReturn(new ArrayList<>());
        response = rest.read();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).get();
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
    void readThrow() {
        when(service.get(anyLong())).thenReturn(null);
        when(httpResponse.response(null)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        response = rest.read(anyLong());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(service, times(1)).get(anyLong());
    }
}