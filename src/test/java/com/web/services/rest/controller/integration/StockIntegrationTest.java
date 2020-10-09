package com.web.services.rest.controller.integration;

import com.web.services.orm.api.StockAPI;
import com.web.services.orm.service.interfaces.StockService;
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
class StockIntegrationTest extends CredentialsRequestTest {

    @Autowired
    private StockService service;

    private final String PATH = "/api/stock";

    private final Integer updatedQuantity = 42;

    private StockAPI updateAPI = new StockAPI(updatedQuantity);
    private StockAPI invalidAPI = new StockAPI(-1);

    private final String productPath = "/1";

    @Test
    @Order(4)
    @DisplayName("Update a stock")
    void update() {
        HttpEntity<StockAPI> request = new HttpEntity<>(updateAPI, headers);
        response = rest.exchange(PATH + productPath, HttpMethod.PATCH, request, Boolean.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody());
    }

    @Test
    @Order(8)
    @DisplayName("Throw: Invalid value to update a stock")
    void updateThrow() {
        HttpEntity<StockAPI> request = new HttpEntity<>(invalidAPI, headers);
        response = rest.exchange(PATH + productPath, HttpMethod.PATCH, request, HttpExceptionResponseImpl.class);
        assertNotNull(bindingException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}