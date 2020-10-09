package com.web.services.orm.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StockAPI {

    @NotNull
    @Min(value = 0)
    @ApiModelProperty(position = 1, example = "20")
    private Integer quantity = 0;

    public StockAPI() {}

    public StockAPI(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        Formatter formatter = new ValueFormatter().add("quantity", quantity);
        return formatter.format();
    }

    public String logString() {
        return this.quantity.toString();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
