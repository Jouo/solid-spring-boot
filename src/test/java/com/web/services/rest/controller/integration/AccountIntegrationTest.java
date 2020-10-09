package com.web.services.rest.controller.integration;

import com.web.services.orm.api.EpochBanDateAPI;
import com.web.services.orm.api.UserAPI;
import com.web.services.orm.entity.login.AccountDetails;
import com.web.services.orm.entity.login.UserAccount;
import com.web.services.orm.service.interfaces.AccountDetailsService;
import com.web.services.orm.service.interfaces.UserAccountService;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountIntegrationTest extends CredentialsRequestTest {

    @Autowired
    private UserAccountService accountService;

    @Autowired
    private AccountDetailsService detailsService;

    private final String PATH = "/api/user";

    private AccountDetails details;
    private UserAccount account;
    private UserAccount[] entities;

    private final String updatedName = "Updated Name";
    private final String updatedLastName = "Updated LastName";
    private final String updatedUsername = "Updated Username";
    private final String updatedPassword = "Updated Password";
    private final List<Long> updatedRoles = List.of(2L);

    private EpochBanDateAPI validBanDate = EpochBanDateAPI.fromHours(1);
    private EpochBanDateAPI invalidBanDate = new EpochBanDateAPI(123456789L);

    private UserAPI newAPI = new UserAPI(
            "Name", "LastName", "Username", "Password", List.of(1L));

    private UserAPI updateAPI = new UserAPI(
            updatedName, updatedLastName, updatedUsername, updatedPassword, updatedRoles);

    private UserAPI invalidAPI = new UserAPI(
            "", "", "", "", List.of(3L));

    private final Integer currentEntities = 2;
    private final Long entityID = 3L;
    private final String entityPath = "/3";

    @Test
    @Order(2)
    @DisplayName("True: Lock a user")
    void toggleLockTrue() {
        response = rest.exchange(PATH + "/lock/2", HttpMethod.PATCH, token, Boolean.class);
        assertTrue((Boolean) response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(3)
    @DisplayName("True: Temporal user ban")
    void temporalBan() {
        HttpEntity<EpochBanDateAPI> request = new HttpEntity<>(validBanDate, headers);
        response = rest.exchange(PATH + "/ban/2", HttpMethod.PATCH, request, Boolean.class);
        assertTrue((Boolean) response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(4)
    @DisplayName("Retrieve the list of 2 users")
    void readList() {
        response = rest.exchange(PATH, HttpMethod.GET, token, UserAccount[].class);
        entities = (UserAccount[]) response.getBody();
        assertNotNull(entities);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(currentEntities, entities.length);
    }

    @Test
    @Order(5)
    @DisplayName("Add a new user")
    void create() {
        HttpEntity<UserAPI> request = new HttpEntity<>(newAPI, headers);
        response = rest.exchange(PATH, HttpMethod.POST, request, UserAccount.class);
        account = (UserAccount) response.getBody();
        assertNotNull(account);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account.getId(), entityID);
    }

    @Test
    @Order(6)
    @DisplayName("Update the new user")
    void update() {
        HttpEntity<UserAPI> request = new HttpEntity<>(updateAPI, headers);
        response = rest.exchange(PATH + entityPath, HttpMethod.PATCH, request, Boolean.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody());
    }

    @Test
    @Order(7)
    @DisplayName("Retrieve the updated user")
    void read() {
        response = rest.exchange(PATH + entityPath, HttpMethod.GET, token, UserAccount.class);
        account = (UserAccount) response.getBody();
        details = account.getAccountDetails();
        assertNotNull(account);
        assertEquals(updatedName, account.getName());
        assertEquals(updatedLastName, account.getLastName());
        assertEquals(updatedUsername, details.getUsername());
        assertTrue(details.rolesMatch(updatedRoles));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(8)
    @DisplayName("False: Lock a user")
    void toggleLockFalse() {
        response = rest.exchange(PATH + "/lock/99", HttpMethod.PATCH, token, Boolean.class);
        assertFalse((Boolean) response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(9)
    @DisplayName("Throw: Invalid epoch ban date")
    void temporalBanThrow() {
        HttpEntity<EpochBanDateAPI> request = new HttpEntity<>(invalidBanDate, headers);
        response = rest.exchange(PATH + "/ban/2", HttpMethod.PATCH, request, BindingExceptionResponseImpl.class);
        bindingException = (BindingExceptionResponse) response.getBody();
        assertNotNull(bindingException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(10)
    @DisplayName("Throw: Save an invalid user")
    void createThrow() {
        HttpEntity<UserAPI> request = new HttpEntity<>(invalidAPI, headers);
        response = rest.exchange(PATH, HttpMethod.POST, request, BindingExceptionResponseImpl.class);
        bindingException = (BindingExceptionResponse) response.getBody();
        assertNotNull(bindingException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(11)
    @DisplayName("Throw: User not found (null)")
    void readThrow() {
        response = rest.exchange(PATH + "/99", HttpMethod.GET, token, HttpExceptionResponseImpl.class);
        httpException = (HttpExceptionResponse) response.getBody();
        assertNotNull(httpException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(12)
    @DisplayName("Throw: Invalid values to update a user")
    void updateThrow() {
        HttpEntity<UserAPI> request = new HttpEntity<>(invalidAPI, headers);
        response = rest.exchange(PATH + entityPath, HttpMethod.PATCH, request, HttpExceptionResponseImpl.class);
        assertNotNull(bindingException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}