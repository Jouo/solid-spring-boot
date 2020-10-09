package com.web.services.rest.controller.integration;

import com.web.services.orm.api.ProviderAPI;
import com.web.services.orm.entity.configuration.Provider;
import com.web.services.orm.service.interfaces.ProviderService;
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
class ProviderIntegrationTest extends CredentialsRequestTest {

    @Autowired
    private ProviderService service;

    private final String PATH = "/api/provider";

    private Provider entity;
    private Provider[] entities;

    private final String updatedName = "Updated Name";
    private final String updatedCompany = "Updated Company";
    private final String updatedPhone = "Updated Phone";

    private ProviderAPI newAPI = new ProviderAPI("Name", "Company", "Phone");
    private ProviderAPI updateAPI = new ProviderAPI(updatedName, updatedCompany, updatedPhone);
    private ProviderAPI invalidAPI = new ProviderAPI();

    private final Integer currentEntities = 3;
    private final Long entityID = 4L;
    private final String entityPath = "/4";

    @Test
    @Order(2)
    @DisplayName("Retrieve the list of 3 providers")
    void readList() {
        response = rest.exchange(PATH, HttpMethod.GET, token, Provider[].class);
        entities = (Provider[]) response.getBody();
        assertNotNull(entities);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(currentEntities, entities.length);
    }

    @Test
    @Order(3)
    @DisplayName("Add a new provider")
    void create() {
        HttpEntity<ProviderAPI> request = new HttpEntity<>(newAPI, headers);
        response = rest.exchange(PATH, HttpMethod.POST, request, Provider.class);
        entity = (Provider) response.getBody();
        assertNotNull(entity);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(entity.getId(), entityID);
    }

    @Test
    @Order(4)
    @DisplayName("Update the new provider")
    void update() {
        HttpEntity<ProviderAPI> request = new HttpEntity<>(updateAPI, headers);
        response = rest.exchange(PATH + entityPath, HttpMethod.PATCH, request, Boolean.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody());
    }

    @Test
    @Order(5)
    @DisplayName("Retrieve the updated provider")
    void read() {
        response = rest.exchange(PATH + entityPath, HttpMethod.GET, token, Provider.class);
        entity = (Provider) response.getBody();
        assertNotNull(entity);
        assertEquals(updatedName, entity.getName());
        assertEquals(updatedCompany, entity.getCompany());
        assertEquals(updatedPhone, entity.getPhone());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(6)
    @DisplayName("Throw: Save an invalid provider")
    void createThrow() {
        HttpEntity<ProviderAPI> request = new HttpEntity<>(invalidAPI, headers);
        response = rest.exchange(PATH, HttpMethod.POST, request, BindingExceptionResponseImpl.class);
        bindingException = (BindingExceptionResponse) response.getBody();
        assertNotNull(bindingException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(7)
    @DisplayName("Throw: Provider not found (null)")
    void readThrow() {
        response = rest.exchange(PATH + "/99", HttpMethod.GET, token, HttpExceptionResponseImpl.class);
        httpException = (HttpExceptionResponse) response.getBody();
        assertNotNull(httpException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(8)
    @DisplayName("Throw: Invalid values to update a provider")
    void updateThrow() {
        HttpEntity<ProviderAPI> request = new HttpEntity<>(invalidAPI, headers);
        response = rest.exchange(PATH + entityPath, HttpMethod.PATCH, request, HttpExceptionResponseImpl.class);
        assertNotNull(bindingException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}