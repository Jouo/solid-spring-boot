package com.web.services.rest.controller;

import com.web.services.orm.api.StockAPI;
import com.web.services.orm.service.interfaces.StockService;
import com.web.services.rest.utility.http.BindingResponseFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/stock")
@Api(tags = {"Stock"}, description = "Modify product's stock in case of mismatch")
public class StockREST {

    private StockService stockService;
    private BindingResponseFactory bindingResponse;

    public StockREST(StockService stockService, BindingResponseFactory bindingResponse) {
        this.stockService = stockService;
        this.bindingResponse = bindingResponse;
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update stock by product id", response = Boolean.class)
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody StockAPI api, BindingResult result) {
        if (result.hasErrors()) { return this.bindingResponse.response(result); }
        boolean updated = stockService.update(id, api);
        return ResponseEntity.ok(updated);
    }
}


