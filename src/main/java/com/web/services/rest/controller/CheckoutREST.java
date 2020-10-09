package com.web.services.rest.controller;

import com.web.services.orm.entity.transaction.Checkout;
import com.web.services.orm.entity.transaction.Purchase;
import com.web.services.orm.service.interfaces.CheckoutService;
import com.web.services.rest.utility.http.HttpResponseFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
@Api(tags = {"Checkout"}, description = "Daily employee checkout containing all transactions")
public class CheckoutREST {

    private CheckoutService checkoutService;
    private HttpResponseFactory httpResponse;

    public CheckoutREST(CheckoutService checkoutService, HttpResponseFactory httpResponse) {
        this.checkoutService = checkoutService;
        this.httpResponse = httpResponse;
    }

    @PostMapping("/{id}")
    @ApiOperation(value = "Generate checkout by user id", response = Checkout.class)
    public ResponseEntity<?> create(@PathVariable Long id) {
        Checkout checkout = checkoutService.generate(id);
        return this.httpResponse.response(checkout);
    }

    @GetMapping("/user/{id}")
    @ApiOperation(value = "Retrieve all checkouts by user id", response = Checkout.class, responseContainer = "List")
    public ResponseEntity<?> readByUser(@PathVariable Long id) {
        return ResponseEntity.ok(checkoutService.getByUser(id));
    }

    @GetMapping
    @ApiOperation(value = "Retrieve all checkouts", response = Checkout.class, responseContainer = "List")
    public ResponseEntity<?> read() {
        return ResponseEntity.ok(checkoutService.get());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieve a checkout by id", response = Checkout.class)
    public ResponseEntity<?> read(@PathVariable Long id) {
        Checkout checkout = checkoutService.get(id);
        return this.httpResponse.response(checkout);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a checkout by id", response = Boolean.class)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean updated = checkoutService.delete(id);
        return ResponseEntity.ok(updated);
    }
}


