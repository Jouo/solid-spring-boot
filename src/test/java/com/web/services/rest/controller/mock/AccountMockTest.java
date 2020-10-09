package com.web.services.rest.controller.mock;

import com.web.services.orm.api.EpochBanDateAPI;
import com.web.services.orm.api.ProviderAPI;
import com.web.services.orm.api.UserAPI;
import com.web.services.orm.entity.configuration.Provider;
import com.web.services.orm.entity.login.UserAccount;
import com.web.services.orm.service.interfaces.AccountDetailsService;
import com.web.services.orm.service.interfaces.ProviderService;
import com.web.services.orm.service.interfaces.UserAccountService;
import com.web.services.rest.controller.AccountREST;
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
class AccountMockTest {

    @InjectMocks
    private AccountREST rest;

    @Mock
    private AccountDetailsService detailsService;

    @Mock
    private UserAccountService accountService;

    @Mock
    private UserAccount entity;

    @Mock
    private UserAPI api;

    @Mock
    private BindingResult binding;

    @Mock
    private BindingResponseFactory bindingResponse;

    @Mock
    private HttpResponseFactory httpResponse;

    @Mock
    private EpochBanDateAPI epochBanDateAPI;

    private ResponseEntity<?> response;

    @Test
    @Order(1)
    void toggleLock() {
        when(detailsService.toggleLock(anyLong())).thenReturn(true);
        when(httpResponse.response(anyBoolean())).thenCallRealMethod();
        response = rest.toggleLock(anyLong());
        verify(detailsService, times(1)).toggleLock(anyLong());
        verifyNoInteractions(accountService);
    }

    @Test
    @Order(2)
    void temporalBan() {
        when(binding.hasErrors()).thenReturn(false);
        when(detailsService.ban(0L, epochBanDateAPI)).thenReturn(true);
        when(httpResponse.response(true)).thenCallRealMethod();
        response = rest.temporalBan(0L, epochBanDateAPI, binding);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verifyNoInteractions(bindingResponse);
        verifyNoInteractions(accountService);
    }

    @Test
    @Order(3)
    void create() {
        when(binding.hasErrors()).thenReturn(false);
        when(accountService.save(api)).thenReturn(entity);
        when(httpResponse.response(entity)).thenCallRealMethod();
        response = rest.create(api, binding);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(accountService, times(1)).save(api);
        verifyNoInteractions(detailsService);
        verifyNoInteractions(bindingResponse);
    }

    @Test
    @Order(4)
    void readList() {
        when(accountService.get()).thenReturn(new ArrayList<>());
        response = rest.read();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(accountService, times(1)).get();
        verifyNoInteractions(detailsService);
    }

    @Test
    @Order(5)
    void read() {
        when(accountService.get(anyLong())).thenReturn(entity);
        when(httpResponse.response(entity)).thenCallRealMethod();
        response = rest.read(anyLong());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(accountService, times(1)).get(anyLong());
        verifyNoInteractions(detailsService);
    }

    @Test
    @Order(6)
    void update() {
        when(binding.hasErrors()).thenReturn(false);
        when(accountService.update(0L, api)).thenReturn(true);
        response = rest.update(0L, api, binding);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(accountService, times(1)).update(0L, api);
        verifyNoInteractions(detailsService);
        verifyNoInteractions(bindingResponse);
    }

    @Test
    @Order(7)
    void delete() {
        when(accountService.delete(0L)).thenReturn(true);
        response = rest.delete(0L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(accountService, times(1)).delete(0L);
        verifyNoInteractions(detailsService);
    }

    @Test
    @Order(8)
    void temporalBanThrow() {
        when(binding.hasErrors()).thenReturn(true);
        when(bindingResponse.response(binding)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        response = rest.temporalBan(0L, epochBanDateAPI, binding);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verifyNoInteractions(detailsService);
        verifyNoInteractions(accountService);
    }

    @Test
    @Order(9)
    void createThrow() {
        when(binding.hasErrors()).thenReturn(true);
        when(bindingResponse.response(binding)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        response = rest.create(api, binding);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verifyNoInteractions(accountService);
        verifyNoInteractions(detailsService);
    }

    @Test
    @Order(10)
    void readThrow() {
        when(accountService.get(anyLong())).thenReturn(null);
        when(httpResponse.response(null)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        response = rest.read(anyLong());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(accountService, times(1)).get(anyLong());
        verifyNoInteractions(detailsService);
    }

    @Test
    @Order(11)
    void updateThrow() {
        when(binding.hasErrors()).thenReturn(true);
        when(bindingResponse.response(binding)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        response = rest.update(0L, api, binding);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verifyNoInteractions(accountService);
        verifyNoInteractions(detailsService);
    }
}