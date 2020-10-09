package com.web.services.rest.controller;

import com.web.services.orm.api.PrinterAPI;
import com.web.services.orm.entity.configuration.Printer;
import com.web.services.orm.service.interfaces.PrinterService;
import com.web.services.rest.utility.http.BindingResponseFactory;
import com.web.services.rest.utility.http.HttpResponseFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/printer")
@Api(tags = {"Printer"}, description = "Receipt printers for the cash registers")
public class PrinterREST {

    private PrinterService printerService;
    private BindingResponseFactory bindingResponse;
    private HttpResponseFactory httpResponse;

    public PrinterREST(PrinterService printerService, BindingResponseFactory bindingResponse,
                       HttpResponseFactory httpResponse) {
        this.printerService = printerService;
        this.bindingResponse = bindingResponse;
        this.httpResponse = httpResponse;
    }

    @PostMapping
    @ApiOperation(value = "Save a new printer", response = Printer.class)
    public ResponseEntity<?> create(@Valid @RequestBody PrinterAPI api, BindingResult result) {
        if (result.hasErrors()) { return this.bindingResponse.response(result); }
        Printer printer = printerService.save(api);
        return this.httpResponse.response(printer);
    }

    @GetMapping
    @ApiOperation(value = "Retrieve all printers", response = Printer.class, responseContainer = "List")
    public ResponseEntity<?> read() {
        return ResponseEntity.ok(printerService.get());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieve a printer by id", response = Printer.class)
    public ResponseEntity<?> read(@PathVariable Long id) {
        Printer printer = printerService.get(id);
        return this.httpResponse.response(printer);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update a printer by id", response = Boolean.class)
    public ResponseEntity<?> update(
            @PathVariable Long id, @Valid @RequestBody PrinterAPI api, BindingResult result) {
        if (result.hasErrors()) { return this.bindingResponse.response(result); }
        boolean updated = printerService.update(id, api);
        return ResponseEntity.ok(updated);
    }
}


