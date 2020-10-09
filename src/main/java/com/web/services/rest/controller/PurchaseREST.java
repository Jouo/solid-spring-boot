package com.web.services.rest.controller;

import com.web.services.orm.api.PurchaseAPI;
import com.web.services.orm.entity.transaction.Purchase;
import com.web.services.orm.service.interfaces.PurchaseService;
import com.web.services.rest.utility.http.BindingResponseFactory;
import com.web.services.rest.utility.http.HttpResponseFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/purchase")
@Api(tags = {"Purchase"}, description = "Product purchase from providers")
public class PurchaseREST {

    private PurchaseService purchaseService;
    private BindingResponseFactory bindingResponse;
    private HttpResponseFactory httpResponse;

    public PurchaseREST(PurchaseService purchaseService, BindingResponseFactory bindingResponse,
                        HttpResponseFactory httpResponse) {
        this.purchaseService = purchaseService;
        this.bindingResponse = bindingResponse;
        this.httpResponse = httpResponse;
    }

    @PostMapping
    @ApiOperation(value = "Register a new purchase", response = Purchase.class)
    public ResponseEntity<?> create(@Valid @RequestBody PurchaseAPI api, BindingResult result) {
        if (result.hasErrors()) { return this.bindingResponse.response(result); }
        Purchase purchase = purchaseService.save(api);
        return this.httpResponse.response(purchase);
    }

    @GetMapping("/unchecked/{id}")
    @ApiOperation(value = "Retrieve all purchases that haven't been checkout by user id",
            response = Purchase.class, responseContainer = "List")
    public ResponseEntity<?> readUnchecked(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseService.getUnchecked(id));
    }

    @GetMapping("/checked")
    @ApiOperation(value = "Retrieve all purchases that have been checkout",
            response = Purchase.class, responseContainer = "List")
    public ResponseEntity<?> readChecked() {
        return ResponseEntity.ok(purchaseService.getChecked());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieve a purchase by id", response = Purchase.class)
    public ResponseEntity<?> read(@PathVariable Long id) {
        Purchase purchase = purchaseService.get(id);
        return this.httpResponse.response(purchase);
    }
}


