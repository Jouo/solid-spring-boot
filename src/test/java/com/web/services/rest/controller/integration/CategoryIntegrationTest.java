package com.web.services.rest.controller.integration;

import com.web.services.orm.api.CategoryAPI;
import com.web.services.orm.entity.configuration.Category;
import com.web.services.orm.service.interfaces.CategoryService;
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
class CategoryIntegrationTest extends CredentialsRequestTest {

    @Autowired
    private CategoryService service;

    private final String PATH = "/api/category";

    private Category entity;
    private Category[] entities;

    private final String updatedName = "Updated Name";

    private CategoryAPI newAPI = new CategoryAPI("Name");
    private CategoryAPI updateAPI = new CategoryAPI(updatedName);
    private CategoryAPI invalidAPI = new CategoryAPI();

    private final Integer currentEntities = 5;
    private final Long entityID = 6L;
    private final String entityPath = "/6";

    @Test
    @Order(2)
    @DisplayName("Retrieve the list of 5 categories")
    void readList() {
        response = rest.exchange(PATH, HttpMethod.GET, token, Category[].class);
        entities = (Category[]) response.getBody();
        assertNotNull(entities);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(currentEntities, entities.length);
    }

    @Test
    @Order(3)
    @DisplayName("Add a new category")
    void create() {
        HttpEntity<CategoryAPI> request = new HttpEntity<>(newAPI, headers);
        response = rest.exchange(PATH, HttpMethod.POST, request, Category.class);
        entity = (Category) response.getBody();
        assertNotNull(entity);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(entity.getId(), entityID);
    }

    @Test
    @Order(4)
    @DisplayName("Update the category name")
    void update() {
        HttpEntity<CategoryAPI> request = new HttpEntity<>(updateAPI, headers);
        response = rest.exchange(PATH + entityPath, HttpMethod.PATCH, request, Boolean.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody());
    }

    @Test
    @Order(5)
    @DisplayName("Retrieve the updated category")
    void read() {
        response = rest.exchange(PATH + entityPath, HttpMethod.GET, token, Category.class);
        entity = (Category) response.getBody();
        assertNotNull(entity);
        assertEquals(updatedName, entity.getName());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(6)
    @DisplayName("Throw: Save an invalid category")
    void createThrow() {
        HttpEntity<CategoryAPI> request = new HttpEntity<>(invalidAPI, headers);
        response = rest.exchange(PATH, HttpMethod.POST, request, BindingExceptionResponseImpl.class);
        bindingException = (BindingExceptionResponse) response.getBody();
        assertNotNull(bindingException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(7)
    @DisplayName("Throw: Category not found (null)")
    void readThrow() {
        response = rest.exchange(PATH + "/99", HttpMethod.GET, token, HttpExceptionResponseImpl.class);
        httpException = (HttpExceptionResponse) response.getBody();
        assertNotNull(httpException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(8)
    @DisplayName("Throw: Invalid values to update a category")
    void updateThrow() {
        HttpEntity<CategoryAPI> request = new HttpEntity<>(invalidAPI, headers);
        response = rest.exchange(PATH + entityPath, HttpMethod.PATCH, request, HttpExceptionResponseImpl.class);
        assertNotNull(bindingException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}