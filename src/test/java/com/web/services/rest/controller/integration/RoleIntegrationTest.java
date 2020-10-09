package com.web.services.rest.controller.integration;

import com.web.services.orm.entity.login.AccountRole;
import com.web.services.orm.service.interfaces.AccountRoleService;
import com.web.services.rest.utility.http.response.HttpExceptionResponse;
import com.web.services.rest.utility.http.response.HttpExceptionResponseImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RoleIntegrationTest extends CredentialsRequestTest {

    @Autowired
    private AccountRoleService service;

    private final String PATH = "/api/role";

    private AccountRole entity;
    private AccountRole[] entities;

    private final String currentDescription = "Administrator";

    private final Integer currentEntities = 2;
    private final String entityPath = "/1";

    @Test
    @Order(2)
    @DisplayName("Retrieve the list of 2 roles")
    void readList() {
        response = rest.exchange(PATH, HttpMethod.GET, token, AccountRole[].class);
        entities = (AccountRole[]) response.getBody();
        assertNotNull(entities);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(currentEntities, entities.length);
    }

    @Test
    @Order(3)
    @DisplayName("Retrieve a role")
    void read() {
        response = rest.exchange(PATH + entityPath, HttpMethod.GET, token, AccountRole.class);
        entity = (AccountRole) response.getBody();
        assertNotNull(entity);
        assertEquals(currentDescription, entity.getDescription());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(4)
    @DisplayName("Throw: Role not found (null)")
    void readThrow() {
        response = rest.exchange(PATH + "/99", HttpMethod.GET, token, HttpExceptionResponseImpl.class);
        httpException = (HttpExceptionResponse) response.getBody();
        assertNotNull(httpException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}