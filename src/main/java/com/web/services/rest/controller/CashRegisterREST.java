package com.web.services.rest.controller;

import com.web.services.orm.api.CashRegisterAPI;
import com.web.services.orm.entity.configuration.CashRegister;
import com.web.services.orm.service.interfaces.CashRegisterService;
import com.web.services.rest.utility.http.BindingResponseFactory;
import com.web.services.rest.utility.http.HttpResponseFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cashregister")
@Api(tags = {"Cash Register"}, description = "Cash registers used by employees")
public class CashRegisterREST {

    private CashRegisterService cashRegisterService;
    private BindingResponseFactory bindingResponse;
    private HttpResponseFactory httpResponse;

    public CashRegisterREST(CashRegisterService cashRegisterService, BindingResponseFactory bindingResponse,
                            HttpResponseFactory httpResponse) {
        this.cashRegisterService = cashRegisterService;
        this.bindingResponse = bindingResponse;
        this.httpResponse = httpResponse;
    }

    @PostMapping
    @ApiOperation(value = "Save a new cash register", response = CashRegister.class)
    public ResponseEntity<?> create(@RequestBody @Valid CashRegisterAPI api, BindingResult result) {
        if (result.hasErrors()) { return this.bindingResponse.response(result); }
        CashRegister cashRegister = cashRegisterService.save(api);
        return this.httpResponse.response(cashRegister);
    }

    @GetMapping
    @ApiOperation(value = "Retrieve all cash registers", response = CashRegister.class, responseContainer = "List")
    public ResponseEntity<?> read() {
        return ResponseEntity.ok(cashRegisterService.get());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieve a cash register by id", response = CashRegister.class)
    public ResponseEntity<?> read(@PathVariable Long id) {
        CashRegister cashRegister = cashRegisterService.get(id);
        return this.httpResponse.response(cashRegister);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update a cash register by id", response = Boolean.class)
    public ResponseEntity<?> update(
            @PathVariable Long id, @Valid @RequestBody CashRegisterAPI api, BindingResult result) {
        if (result.hasErrors()) { return this.bindingResponse.response(result); }
        boolean updated = cashRegisterService.update(id, api);
        return ResponseEntity.ok(updated);
    }
}


