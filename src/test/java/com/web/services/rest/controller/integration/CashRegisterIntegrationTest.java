package com.web.services.rest.controller.integration;

import com.web.services.orm.api.CashRegisterAPI;
import com.web.services.orm.entity.configuration.CashRegister;
import com.web.services.orm.service.interfaces.CashRegisterService;
import com.web.services.rest.utility.http.response.BindingExceptionResponse;
import com.web.services.rest.utility.http.response.BindingExceptionResponseImpl;
import com.web.services.rest.utility.http.response.HttpExceptionResponse;
import com.web.services.rest.utility.http.response.HttpExceptionResponseImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CashRegisterIntegrationTest extends CredentialsRequestTest {

    @Autowired
    private CashRegisterService service;

    private final String PATH = "/api/cashregister";

    private CashRegister entity;
    private CashRegister[] entities;

    private final String updatedName = "Updated Name";
    private final Long updatedPrinter = 2L;

    private CashRegisterAPI newAPI = new CashRegisterAPI("Name", 1L);
    private CashRegisterAPI updateAPI = new CashRegisterAPI(updatedName, updatedPrinter);
    private CashRegisterAPI invalidAPI = new CashRegisterAPI("", 99L);

    private final Integer currentEntities = 2;
    private final Long entityID = 3L;
    private final String entityPath = "/3";

    @Test
    @Order(2)
    @DisplayName("Retrieve the list of 2 cash registers")
    void readList() {
        response = rest.exchange(PATH, HttpMethod.GET, token, CashRegister[].class);
        entities = (CashRegister[]) response.getBody();
        assertNotNull(entities);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(currentEntities, entities.length);
    }

    @Test
    @Order(3)
    @DisplayName("Add a new cash register")
    void create() {
        HttpEntity<CashRegisterAPI> request = new HttpEntity<>(newAPI, headers);
        response = rest.exchange(PATH, HttpMethod.POST, request, CashRegister.class);
        entity = (CashRegister) response.getBody();
        assertNotNull(entity);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(entity.getId(), entityID);
    }

    @Test
    @Order(4)
    @DisplayName("Update the new cash register")
    void update() {
        HttpEntity<CashRegisterAPI> request = new HttpEntity<>(updateAPI, headers);
        response = rest.exchange(PATH + entityPath, HttpMethod.PATCH, request, Boolean.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody());
    }

    @Test
    @Order(5)
    @DisplayName("Retrieve the updated cash register")
    void read() {
        response = rest.exchange(PATH + entityPath, HttpMethod.GET, token, CashRegister.class);
        entity = (CashRegister) response.getBody();
        assertNotNull(entity);
        assertEquals(updatedName, entity.getName());
        assertEquals(updatedPrinter, entity.getPrinter().getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(6)
    @DisplayName("Throw: Save an invalid cash register")
    void createThrow() {
        HttpEntity<CashRegisterAPI> request = new HttpEntity<>(invalidAPI, headers);
        response = rest.exchange(PATH, HttpMethod.POST, request, BindingExceptionResponseImpl.class);
        bindingException = (BindingExceptionResponse) response.getBody();
        assertNotNull(bindingException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(7)
    @DisplayName("Throw: Cash register not found (null)")
    void readThrow() {
        response = rest.exchange(PATH + "/99", HttpMethod.GET, token, HttpExceptionResponseImpl.class);
        httpException = (HttpExceptionResponse) response.getBody();
        assertNotNull(httpException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(8)
    @DisplayName("Throw: Invalid values to update a cash register")
    void updateThrow() {
        HttpEntity<CashRegisterAPI> request = new HttpEntity<>(invalidAPI, headers);
        response = rest.exchange(PATH + entityPath, HttpMethod.PATCH, request, HttpExceptionResponseImpl.class);
        assertNotNull(bindingException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}