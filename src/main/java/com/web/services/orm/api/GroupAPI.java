package com.web.services.orm.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.services.Utility;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.validator.intercaces.MustExist;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupAPI {

    @NotNull
    @MustExist(className = "Product", message = "Product doesn't exist")
    @ApiModelProperty(position = 1, example = "1")
    private Long id = null;

    @NotNull
    @Min(value = 0)
    @ApiModelProperty(position = 2, example = "2")
    private Integer quantity = null;

    public GroupAPI() {}

    public GroupAPI(Long id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return Utility.arrayFormat(id, quantity);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
