package com.web.services.rest.controller;

import com.web.services.orm.api.SaleAPI;
import com.web.services.orm.entity.transaction.Sale;
import com.web.services.orm.service.interfaces.SaleService;
import com.web.services.rest.utility.http.BindingResponseFactory;
import com.web.services.rest.utility.http.HttpResponseFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/sale")
@Api(tags = {"Sale"}, description = "Product sales")
public class SaleREST {

    private SaleService saleService;
    private BindingResponseFactory bindingResponse;
    private HttpResponseFactory httpResponse;

    public SaleREST(SaleService saleService, BindingResponseFactory bindingResponse,
                    HttpResponseFactory httpResponse) {
        this.saleService = saleService;
        this.bindingResponse = bindingResponse;
        this.httpResponse = httpResponse;
    }

    @PostMapping
    @ApiOperation(value = "Register a new sale", response = Sale.class)
    public ResponseEntity<?> create(@Valid @RequestBody SaleAPI api, BindingResult result) {
        if (result.hasErrors()) { return this.bindingResponse.response(result); }
        Sale sale = saleService.save(api);
        return this.httpResponse.response(sale);
    }

    @GetMapping("/unchecked/{id}")
    @ApiOperation(value = "Retrieve all sales that haven't been checked out by user id",
            response = Sale.class, responseContainer = "List")
    public ResponseEntity<?> readUnchecked(@PathVariable Long id) {
        return ResponseEntity.ok(saleService.getUnchecked(id));
    }

    @GetMapping("/checked")
    @ApiOperation(value = "Retrieve all sales that have been checked out",
            response = Sale.class, responseContainer = "List")
    public ResponseEntity<?> readChecked() {
        return ResponseEntity.ok(saleService.getChecked());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieve a sale by id", response = Sale.class)
    public ResponseEntity<?> read(@PathVariable Long id) {
        Sale sale = saleService.get(id);
        return this.httpResponse.response(sale);
    }
}


