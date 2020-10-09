package com.web.services.rest.controller.integration;

import com.web.services.orm.api.ProductAPI;
import com.web.services.orm.api.StockAPI;
import com.web.services.orm.api.UpdateProductAPI;
import com.web.services.orm.entity.business.Product;
import com.web.services.orm.service.interfaces.ProductService;
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
class ProductIntegrationTest extends CredentialsRequestTest {

    @Autowired
    private ProductService service;

    private final String PATH = "/api/product";

    private Product entity;
    private Product[] entities;

    private final String updatedName = "Updated Name";
    private final Float updatedCost = 2.0f;
    private final Float updatedPrice = 2.0f;
    private final String updatedImage = "Updated Image";
    private final Long updatedCategory = 2L;

    private ProductAPI newAPI = new ProductAPI(
            "Name", 1.0f, 1.0f, "Image", 1L, new StockAPI(10));

    private UpdateProductAPI updateAPI = new UpdateProductAPI(
            updatedName, updatedCost, updatedPrice, updatedImage, updatedCategory);

    private ProductAPI invalidAPI = new ProductAPI(
            "", -1f, -1f, null, 99L, new StockAPI(-1));

    private final Integer currentEntities = 15;
    private final Integer currentCategoryEntities = 3;
    private final Long entityID = 16L;
    private final String entityPath = "/16";

    @Test
    @Order(2)
    @DisplayName("Retrieve the list of 5 products by category")
    void readByCategory() {
        response = rest.exchange(PATH + "/category/1", HttpMethod.GET, token, Product[].class);
        entities = (Product[]) response.getBody();
        assertNotNull(entities);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(currentCategoryEntities, entities.length);
    }

    @Test
    @Order(3)
    @DisplayName("Retrieve the list of 15 products")
    void readList() {
        response = rest.exchange(PATH, HttpMethod.GET, token, Product[].class);
        entities = (Product[]) response.getBody();
        assertNotNull(entities);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(currentEntities, entities.length);
    }

    @Test
    @Order(4)
    @DisplayName("Add a new product")
    void create() {
        HttpEntity<ProductAPI> request = new HttpEntity<>(newAPI, headers);
        response = rest.exchange(PATH, HttpMethod.POST, request, Product.class);
        entity = (Product) response.getBody();
        assertNotNull(entity);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(entity.getId(), entityID);
    }

    @Test
    @Order(5)
    @DisplayName("Update the new product")
    void update() {
        HttpEntity<UpdateProductAPI> request = new HttpEntity<>(updateAPI, headers);
        response = rest.exchange(PATH + entityPath, HttpMethod.PATCH, request, Boolean.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody());
    }

    @Test
    @Order(6)
    @DisplayName("Retrieve the updated product")
    void read() {
        response = rest.exchange(PATH + entityPath, HttpMethod.GET, token, Product.class);
        entity = (Product) response.getBody();
        assertNotNull(entity);
        assertEquals(updatedName, entity.getName());
        assertEquals(updatedCost, entity.getCost());
        assertEquals(updatedPrice, entity.getPrice());
        assertEquals(updatedImage, entity.getImage());
        assertEquals(updatedCategory, entity.getCategory().getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(7)
    @DisplayName("Throw: Save an invalid product")
    void createThrow() {
        HttpEntity<ProductAPI> request = new HttpEntity<>(invalidAPI, headers);
        response = rest.exchange(PATH, HttpMethod.POST, request, BindingExceptionResponseImpl.class);
        bindingException = (BindingExceptionResponse) response.getBody();
        assertNotNull(bindingException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(8)
    @DisplayName("Throw: Product not found (null)")
    void readThrow() {
        response = rest.exchange(PATH + "/99", HttpMethod.GET, token, HttpExceptionResponseImpl.class);
        httpException = (HttpExceptionResponse) response.getBody();
        assertNotNull(httpException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(9)
    @DisplayName("Throw: Invalid values to update a product")
    void updateThrow() {
        HttpEntity<ProductAPI> request = new HttpEntity<>(invalidAPI, headers);
        response = rest.exchange(PATH + entityPath, HttpMethod.PATCH, request, HttpExceptionResponseImpl.class);
        assertNotNull(bindingException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}