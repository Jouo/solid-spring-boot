package com.web.services.rest.controller;

import com.web.services.orm.api.ProductAPI;
import com.web.services.orm.api.UpdateProductAPI;
import com.web.services.orm.entity.business.Product;
import com.web.services.orm.service.interfaces.ProductService;
import com.web.services.rest.utility.http.BindingResponseFactory;
import com.web.services.rest.utility.http.HttpResponseFactory;
import com.web.services.utility.orm.interfaces.ProductUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/product")
@Api(tags = {"Product"}, description = "Available business products")
public class ProductREST {

    private ProductService productService;
    private ProductUtils productUtils;
    private BindingResponseFactory bindingResponse;
    private HttpResponseFactory httpResponse;

    public ProductREST(ProductService productService, ProductUtils productUtils,
                       BindingResponseFactory bindingResponse, HttpResponseFactory httpResponse) {
        this.productService = productService;
        this.productUtils = productUtils;
        this.bindingResponse = bindingResponse;
        this.httpResponse = httpResponse;
    }

    @PostMapping
    @ApiOperation(value = "Save a new product", response = Product.class)
    public ResponseEntity<?> create(@Valid @RequestBody ProductAPI api, BindingResult result) {
        if (result.hasErrors()) { return this.bindingResponse.response(result); }
        Product product = productService.save(api);
        return this.httpResponse.response(product);
    }

    @GetMapping("/category/{id}")
    @ApiOperation(value = "Retrieve all products by category id", response = Product.class, responseContainer = "List")
    public ResponseEntity<?> readByCategory(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getByCategory(id));
    }

    @GetMapping
    @ApiOperation(value = "Retrieve all products", response = Product.class, responseContainer = "List")
    public ResponseEntity<?> read() {
        return ResponseEntity.ok(productService.get());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieve a product by id", response = Product.class)
    public ResponseEntity<?> read(@PathVariable Long id) {
        Product product = productService.get(id);
        return this.httpResponse.response(product);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update a product by id", response = Boolean.class)
    public ResponseEntity<?> update(
            @PathVariable Long id, @Valid @RequestBody UpdateProductAPI api, BindingResult result) {
        if (result.hasErrors()) { return this.bindingResponse.response(result); }
        ProductAPI productAPI = productUtils.fromUpdate(api);
        boolean updated = productService.update(id, productAPI);
        return ResponseEntity.ok(updated);
    }
}


