package com.web.services.rest.controller.integration;

import com.web.services.rest.utility.http.response.BindingExceptionResponse;
import com.web.services.rest.utility.http.response.HttpExceptionResponse;
import com.web.services.security.authentication.AuthenticationRequest;
import com.web.services.security.authentication.AuthenticationResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CredentialsRequestTest {

    @Autowired
    protected TestRestTemplate rest;

    @Autowired
    protected HttpExceptionResponse httpException;

    @Autowired
    protected BindingExceptionResponse bindingException;

    protected ResponseEntity<?> response;
    protected static HttpEntity token;
    protected static HttpHeaders headers = new HttpHeaders();

    private final String AUTHENTICATION = "/api/authentication";
    private AuthenticationRequest authRequest = new AuthenticationRequest("developer", "123");

    @Test
    @Order(1)
    @DisplayName("Setting up credentials (JWT)")
    void setup() {
        HttpEntity<AuthenticationRequest> request = new HttpEntity<>(authRequest);
        response = rest.postForEntity(AUTHENTICATION, request, AuthenticationResponse.class);
        AuthenticationResponse authResponse = (AuthenticationResponse) response.getBody();

        assertNotNull(authResponse);
        assertNotNull(authResponse.getToken());

        headers.set("Authorization", "Bearer " + authResponse.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        token = new HttpEntity(headers);

        rest.getRestTemplate()
                .setRequestFactory(
                        new HttpComponentsClientHttpRequestFactory());
    }
}