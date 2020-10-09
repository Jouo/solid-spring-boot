package com.web.services.orm.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryAPI {

    @NotNull
    @NotBlank
    @ApiModelProperty(position = 1, example = "Beverage")
    private String name = "";

    public CategoryAPI() {}

    public CategoryAPI(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        Formatter formatter = new ValueFormatter().add("name", name);
        return formatter.format();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
