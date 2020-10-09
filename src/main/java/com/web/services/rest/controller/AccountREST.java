package com.web.services.rest.controller;

import com.web.services.orm.api.EpochBanDateAPI;
import com.web.services.orm.api.UserAPI;
import com.web.services.orm.entity.login.UserAccount;
import com.web.services.orm.service.interfaces.AccountDetailsService;
import com.web.services.orm.service.interfaces.UserAccountService;
import com.web.services.rest.utility.http.BindingResponseFactory;
import com.web.services.rest.utility.http.HttpResponseFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@Api(tags = {"User Account"}, description = "User accounts for the application")
public class AccountREST {

    private AccountDetailsService accountDetailsService;
    private UserAccountService userAccountService;
    private BindingResponseFactory bindingResponse;
    private HttpResponseFactory httpResponse;

    public AccountREST(AccountDetailsService accountDetailsService, UserAccountService userAccountService,
                       BindingResponseFactory bindingResponse, HttpResponseFactory httpResponse) {
        this.accountDetailsService = accountDetailsService;
        this.userAccountService = userAccountService;
        this.bindingResponse = bindingResponse;
        this.httpResponse = httpResponse;
    }

    @PatchMapping("/lock/{id}")
    @ApiOperation(value = "Toggles user account lock [ON / OFF], you can't lock yourself",
            response = Boolean.class)
    public ResponseEntity<?> toggleLock(@PathVariable Long id) {
        boolean updated = accountDetailsService.toggleLock(id);
        return this.httpResponse.response(updated);
    }

    @PatchMapping("/ban/{id}")
    @ApiOperation(value = "Temporarily ban a user, date in epoch seconds, you can't ban yourself",
            response = Boolean.class)
    public ResponseEntity<?> temporalBan(
            @PathVariable Long id, @Valid @RequestBody EpochBanDateAPI api, BindingResult result) {
        if (result.hasErrors()) { return this.bindingResponse.response(result); }
        boolean updated = accountDetailsService.ban(id, api);
        return this.httpResponse.response(updated);
    }

    @PostMapping
    @ApiOperation(value = "Register a new user", response = UserAccount.class)
    public ResponseEntity<?> create(@Valid @RequestBody UserAPI userAPI, BindingResult result) {
        if (result.hasErrors()) { return this.bindingResponse.response(result); }
        UserAccount userAccount = userAccountService.save(userAPI);
        return this.httpResponse.response(userAccount);
    }

    @GetMapping
    @ApiOperation(value = "Gets all users", response = UserAccount.class, responseContainer = "List")
    public ResponseEntity<?> read() {
        return ResponseEntity.ok(userAccountService.get());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Gets an user by id", response = UserAccount.class)
    public ResponseEntity<?> read(@PathVariable Long id) {
        UserAccount userAccount = userAccountService.get(id);
        return this.httpResponse.response(userAccount);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Updates an user by id", response = Boolean.class)
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody UserAPI api, BindingResult result) {
        if (result.hasErrors()) { return this.bindingResponse.response(result); }
        boolean updated = userAccountService.update(id, api);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes an user by id", response = Boolean.class)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean updated = userAccountService.delete(id);
        return ResponseEntity.ok(updated);
    }
}


