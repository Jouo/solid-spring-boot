package com.web.services.rest.controller;

import com.web.services.orm.entity.login.AccountRole;
import com.web.services.orm.service.interfaces.AccountRoleService;
import com.web.services.rest.utility.http.HttpResponseFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
@Api(tags = {"Role"}, description = "Roles available for user accounts")
public class RoleREST {

    private AccountRoleService accountRoleService;
    private HttpResponseFactory httpResponse;

    public RoleREST(AccountRoleService accountRoleService, HttpResponseFactory httpResponse) {
        this.accountRoleService = accountRoleService;
        this.httpResponse = httpResponse;
    }

    @GetMapping
    @ApiOperation(value = "Retrieve all roles", response = AccountRole.class, responseContainer = "List")
    public ResponseEntity<?> read() {
        return ResponseEntity.ok(accountRoleService.get());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieve a role by id", response = AccountRole.class)
    public ResponseEntity<?> read(@PathVariable Long id) {
        AccountRole accountRole = accountRoleService.get(id);
        return this.httpResponse.response(accountRole);
    }
}


