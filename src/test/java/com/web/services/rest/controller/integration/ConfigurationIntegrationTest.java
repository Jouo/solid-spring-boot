package com.web.services.rest.controller.integration;

import com.web.services.orm.api.ConfigurationAPI;
import com.web.services.orm.entity.configuration.Configuration;
import com.web.services.orm.service.interfaces.ConfigurationService;
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
class ConfigurationIntegrationTest extends CredentialsRequestTest {

    @Autowired
    private ConfigurationService service;

    private final String PATH = "/api/configuration";

    private Configuration entity;

    private final String updatedCompany = "Updated Company";
    private final String updatedAddress = "Updated Address";
    private final String updatedPhone = "Updated Phone";
    private final String updatedMessage = "Updated Message";

    private ConfigurationAPI newAPI = new ConfigurationAPI(
            "Company", "Address", "Phone", "Message");

    private ConfigurationAPI updateAPI = new ConfigurationAPI(
            updatedCompany, updatedAddress, updatedPhone, updatedMessage);

    private ConfigurationAPI invalidAPI = new ConfigurationAPI();

    @Test
    @Order(2)
    @DisplayName("Update the configuration")
    void update() {
        HttpEntity<ConfigurationAPI> request = new HttpEntity<>(updateAPI, headers);
        response = rest.exchange(PATH, HttpMethod.PATCH, request, Boolean.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody());
    }

    @Test
    @Order(3)
    @DisplayName("Retrieve the updated configuration")
    void read() {
        response = rest.exchange(PATH, HttpMethod.GET, token, Configuration.class);
        entity = (Configuration) response.getBody();
        assertNotNull(entity);
        assertEquals(updatedCompany, entity.getCompany());
        assertEquals(updatedAddress, entity.getAddress());
        assertEquals(updatedPhone, entity.getPhone());
        assertEquals(updatedMessage, entity.getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(4)
    @DisplayName("Throw: Invalid values to update the configuration")
    void updateThrow() {
        HttpEntity<ConfigurationAPI> request = new HttpEntity<>(invalidAPI, headers);
        response = rest.exchange(PATH, HttpMethod.PATCH, request, HttpExceptionResponseImpl.class);
        assertNotNull(bindingException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}