package com.web.services.rest.controller;

import com.web.services.orm.api.ProviderAPI;
import com.web.services.orm.entity.configuration.Provider;
import com.web.services.orm.service.interfaces.ProviderService;
import com.web.services.rest.utility.http.BindingResponseFactory;
import com.web.services.rest.utility.http.HttpResponseFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/provider")
@Api(tags = {"Provider"}, description = "Product providers for the business")
public class ProviderREST {

    private ProviderService providerService;
    private BindingResponseFactory bindingResponse;
    private HttpResponseFactory httpResponse;

    public ProviderREST(ProviderService providerService, BindingResponseFactory bindingResponse,
                        HttpResponseFactory httpResponse) {
        this.providerService = providerService;
        this.bindingResponse = bindingResponse;
        this.httpResponse = httpResponse;
    }

    @PostMapping
    @ApiOperation(value = "Register a new provider", response = Provider.class)
    public ResponseEntity<?> create(@Valid @RequestBody ProviderAPI api, BindingResult result) {
        if (result.hasErrors()) { return this.bindingResponse.response(result); }
        Provider provider = providerService.save(api);
        return this.httpResponse.response(provider);
    }

    @GetMapping
    @ApiOperation(value = "Retrieve all providers", response = Provider.class, responseContainer = "List")
    public ResponseEntity<?> read() {
        return ResponseEntity.ok(providerService.get());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieve a provider by id", response = Provider.class)
    public ResponseEntity<?> read(@PathVariable Long id) {
        Provider provider = providerService.get(id);
        return this.httpResponse.response(provider);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update a provider by id", response = Boolean.class)
    public ResponseEntity<?> update(
            @PathVariable Long id, @Valid @RequestBody ProviderAPI api, BindingResult result) {
        if (result.hasErrors()) { return this.bindingResponse.response(result); }
        boolean updated = providerService.update(id, api);
        return ResponseEntity.ok(updated);
    }
}


