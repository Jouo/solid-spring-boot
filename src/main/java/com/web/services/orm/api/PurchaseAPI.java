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
public class PurchaseAPI {

    @NotNull
    @MustExist(className = "Provider", message = "Provider doesn't exist")
    @ApiModelProperty(position = 1, example = "1")
    private Long provider = null;

    @Valid
    @NotNull
    @NotEmpty
    @ApiModelProperty(position = 2)
    private List<GroupAPI> products = null;

    public PurchaseAPI() {}

    public PurchaseAPI(Long provider, List<GroupAPI> products) {
        this.provider = provider;
        this.products = products;
    }

    @Override
    public String toString() {
        Formatter formatter = new ValueFormatter()
                .add("provider", provider).add("products", products);
        return formatter.format();
    }

    public Long getProvider() {
        return provider;
    }

    public void setProvider(Long provider) {
        this.provider = provider;
    }

    public List<GroupAPI> getProducts() {
        return products;
    }

    public void setProducts(List<GroupAPI> products) {
        this.products = products;
    }
}
