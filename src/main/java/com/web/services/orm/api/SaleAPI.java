package com.web.services.orm.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import com.web.services.validator.intercaces.MustExist;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SaleAPI {

    @NotNull
    @MustExist(className = "CashRegister", message = "Cash register doesn't exist")
    @ApiModelProperty(position = 1, example = "1")
    private Long cashRegister = null;

    @Valid
    @NotNull
    @NotEmpty
    @ApiModelProperty(position = 2)
    private List<GroupAPI> products = null;

    public SaleAPI() {}

    public SaleAPI(Long cashRegister, List<GroupAPI> products) {
        this.cashRegister = cashRegister;
        this.products = products;
    }

    @Override
    public String toString() {
        Formatter formatter = new ValueFormatter()
                .add("cash register", cashRegister).add("products", products);
        return formatter.format();
    }

    public Long getCashRegister() {
        return cashRegister;
    }

    public void setCashRegister(Long cashRegister) {
        this.cashRegister = cashRegister;
    }

    public List<GroupAPI> getProducts() {
        return products;
    }

    public void setProducts(List<GroupAPI> products) {
        this.products = products;
    }
}
