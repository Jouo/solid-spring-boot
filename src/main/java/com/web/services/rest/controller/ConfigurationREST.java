package com.web.services.rest.controller;

import com.web.services.orm.api.ConfigurationAPI;
import com.web.services.orm.entity.configuration.Configuration;
import com.web.services.orm.service.interfaces.ConfigurationService;
import com.web.services.rest.utility.http.BindingResponseFactory;
import com.web.services.rest.utility.http.HttpResponseFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/configuration")
@Api(tags = {"Configuration"}, description = "Company information displayed in the receipts")
public class ConfigurationREST {

    private ConfigurationService configurationService;
    private BindingResponseFactory bindingResponse;
    private HttpResponseFactory httpResponse;

    public ConfigurationREST(ConfigurationService configurationService, BindingResponseFactory bindingResponse,
                             HttpResponseFactory httpResponse) {
        this.configurationService = configurationService;
        this.bindingResponse = bindingResponse;
        this.httpResponse = httpResponse;
    }

    @GetMapping
    @ApiOperation(value = "Retrieve the company information", response = Configuration.class)
    public ResponseEntity<?> read() {
        Configuration configuration = configurationService.get(1L);
        return this.httpResponse.response(configuration);
    }

    @PatchMapping
    @ApiOperation(value = "Update the company information", response = Boolean.class)
    public ResponseEntity<?> update(@Valid @RequestBody ConfigurationAPI api, BindingResult result) {
        if (result.hasErrors()) { return this.bindingResponse.response(result); }
        boolean updated = configurationService.update(1L, api);
        return ResponseEntity.ok(updated);
    }
}


