package com.web.services.security;

import com.web.services.Setting;
import com.web.services.orm.entity.login.UserAccount;
import com.web.services.rest.utility.http.BindingResponseFactory;
import com.web.services.rest.utility.http.HttpResponseFactory;
import com.web.services.rest.utility.http.status.Http400BadRequest;
import com.web.services.rest.utility.http.status.Http401Unauthorized;
import com.web.services.rest.utility.http.status.Http403Forbidden;
import com.web.services.rest.utility.http.status.Http500InternalServerError;
import com.web.services.security.authentication.AuthenticationRequest;
import com.web.services.security.authentication.AuthenticationResponse;
import com.web.services.security.authentication.listener.FailureListener;
import com.web.services.security.jwt.JwtUtilityTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/authentication")
@Api(tags = {"Authentication"}, description = "Send the username and password to authenticate")
public class AuthenticationREST {

    private AuthenticationManager authenticationManager;
    private JwtUtilityTool jwtUtilityTool;
    private FailureListener failureListener;
    private HttpServletRequest request;
    private BindingResponseFactory bindingResponse;
    private HttpResponseFactory httpResponse;

    public AuthenticationREST(AuthenticationManager authenticationManager, JwtUtilityTool jwtUtilityTool,
                              FailureListener failureListener, HttpServletRequest request,
                              BindingResponseFactory bindingResponse, HttpResponseFactory httpResponse) {
        this.authenticationManager = authenticationManager;
        this.jwtUtilityTool = jwtUtilityTool;
        this.failureListener = failureListener;
        this.request = request;
        this.bindingResponse = bindingResponse;
        this.httpResponse = httpResponse;
    }

    @PostMapping
    @ApiOperation(value = "Request the JSON Web Token along the user's account information"
            , response = AuthenticationResponse.class)
    public ResponseEntity<?> authentication(@Valid @RequestBody AuthenticationRequest request, BindingResult result) {

        String ip = this.request.getRemoteAddr();
        this.failureListener.save(ip);
        Authentication authentication;

        try {
            if (result.hasErrors()) {
                if (!banned(ip)) {
                    this.failureListener.loginFailed(ip);
                    return this.bindingResponse.response(result);
                }
                else {
                    return this.httpResponse.exceptionResponse(
                            new Http403Forbidden(Setting.AUTH_IP_BAN_MESSAGE));
                }
            }

            if (banned(ip)) {
                return this.httpResponse.exceptionResponse(
                        new Http403Forbidden(Setting.AUTH_IP_BAN_MESSAGE));
            }

            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword())
            );
        }
        catch (BadCredentialsException exception) {
            return this.httpResponse.exceptionResponse(
                    new Http400BadRequest(Setting.AUTH_CREDENTIALS_MESSAGE));
        }
        catch (LockedException exception) {
            return this.httpResponse.exceptionResponse(
                    new Http401Unauthorized(Setting.AUTH_LOCKED_MESSAGE));
        }
        catch (AccountExpiredException exception) {
            return this.httpResponse.exceptionResponse(
                    new Http401Unauthorized(Setting.AUTH_ACCOUNT_BAN_MESSAGE));
        }
        catch (AuthenticationException exception) {
            return this.httpResponse.exceptionResponse(
                    new Http500InternalServerError(exception.getMessage()));
        }

        String jwt = jwtUtilityTool.generateToken(
                (UserDetails) authentication.getPrincipal());

        UserInformation userInformation = (UserInformation) authentication.getPrincipal();
        UserAccount user = userInformation.getUserAccount();

        return ResponseEntity.ok(new AuthenticationResponse(jwt, user));
    }

    private boolean banned(String ip) {
        return this.failureListener.isBlocked(ip);
    }
}
