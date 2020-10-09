package com.web.services.rest.controller.integration;

import com.web.services.orm.api.PrinterAPI;
import com.web.services.orm.entity.configuration.Printer;
import com.web.services.orm.service.interfaces.PrinterService;
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
class PrinterIntegrationTest extends CredentialsRequestTest {

    @Autowired
    private PrinterService service;

    private final String PATH = "/api/printer";

    private Printer entity;
    private Printer[] entities;

    private final String updatedName = "Updated Name";
    private final String updatedIP = "Updated IP";

    private PrinterAPI newAPI = new PrinterAPI("Name", "IP");
    private PrinterAPI updateAPI = new PrinterAPI(updatedName, updatedIP);
    private PrinterAPI invalidAPI = new PrinterAPI();

    private final Integer currentEntities = 2;
    private final Long entityID = 3L;
    private final String entityPath = "/3";

    @Test
    @Order(2)
    @DisplayName("Retrieve the list of 2 printers")
    void readList() {
        response = rest.exchange(PATH, HttpMethod.GET, token, Printer[].class);
        entities = (Printer[]) response.getBody();
        assertNotNull(entities);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(currentEntities, entities.length);
    }

    @Test
    @Order(3)
    @DisplayName("Add a new printer")
    void create() {
        HttpEntity<PrinterAPI> request = new HttpEntity<>(newAPI, headers);
        response = rest.exchange(PATH, HttpMethod.POST, request, Printer.class);
        entity = (Printer) response.getBody();
        assertNotNull(entity);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(entity.getId(), entityID);
    }

    @Test
    @Order(4)
    @DisplayName("Update the new printer")
    void update() {
        HttpEntity<PrinterAPI> request = new HttpEntity<>(updateAPI, headers);
        response = rest.exchange(PATH + entityPath, HttpMethod.PATCH, request, Boolean.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody());
    }

    @Test
    @Order(5)
    @DisplayName("Retrieve the updated printer")
    void read() {
        response = rest.exchange(PATH + entityPath, HttpMethod.GET, token, Printer.class);
        entity = (Printer) response.getBody();
        assertNotNull(entity);
        assertEquals(updatedName, entity.getName());
        assertEquals(updatedIP, entity.getIp());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(6)
    @DisplayName("Throw: Save an invalid printer")
    void createThrow() {
        HttpEntity<PrinterAPI> request = new HttpEntity<>(invalidAPI, headers);
        response = rest.exchange(PATH, HttpMethod.POST, request, BindingExceptionResponseImpl.class);
        bindingException = (BindingExceptionResponse) response.getBody();
        assertNotNull(bindingException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(7)
    @DisplayName("Throw: Printer not found (null)")
    void readThrow() {
        response = rest.exchange(PATH + "/99", HttpMethod.GET, token, HttpExceptionResponseImpl.class);
        httpException = (HttpExceptionResponse) response.getBody();
        assertNotNull(httpException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(8)
    @DisplayName("Throw: Invalid values to update a printer")
    void updateThrow() {
        HttpEntity<PrinterAPI> request = new HttpEntity<>(invalidAPI, headers);
        response = rest.exchange(PATH + entityPath, HttpMethod.PATCH, request, HttpExceptionResponseImpl.class);
        assertNotNull(bindingException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}