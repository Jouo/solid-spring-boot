package com.web.services.rest.controller;

import com.web.services.orm.api.CategoryAPI;
import com.web.services.orm.entity.configuration.Category;
import com.web.services.orm.service.interfaces.CategoryService;
import com.web.services.rest.utility.http.BindingResponseFactory;
import com.web.services.rest.utility.http.HttpResponseFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/category")
@Api(tags = {"Category"}, description = "Categories to organize the products")
public class CategoryREST {

    private CategoryService categoryService;
    private BindingResponseFactory bindingResponse;
    private HttpResponseFactory httpResponse;

    public CategoryREST(CategoryService categoryService, BindingResponseFactory bindingResponse,
                        HttpResponseFactory httpResponse) {
        this.categoryService = categoryService;
        this.bindingResponse = bindingResponse;
        this.httpResponse = httpResponse;
    }

    @PostMapping
    @ApiOperation(value = "Save a new category", response = Category.class)
    public ResponseEntity<?> create(@Valid @RequestBody CategoryAPI api, BindingResult result) {
        if (result.hasErrors()) { return this.bindingResponse.response(result); }
        Category category = categoryService.save(api);
        return this.httpResponse.response(category);
    }

    @GetMapping
    @ApiOperation(value = "Retrieve all categories", response = Category.class, responseContainer = "List")
    public ResponseEntity<?> read() {
        return ResponseEntity.ok(categoryService.get());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieve a category by id", response = Category.class)
    public ResponseEntity<?> read(@PathVariable Long id) {
        Category category = categoryService.get(id);
        return this.httpResponse.response(category);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update a category by id", response = Boolean.class)
    public ResponseEntity<?> update(
            @PathVariable Long id, @Valid @RequestBody CategoryAPI api, BindingResult result) {
        if (result.hasErrors()) { return this.bindingResponse.response(result); }
        boolean updated = categoryService.update(id, api);
        return ResponseEntity.ok(updated);
    }
}


