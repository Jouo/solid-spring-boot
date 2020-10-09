package com.web.services.orm.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProviderAPI {

    @NotNull
    @NotBlank
    @ApiModelProperty(position = 1, example = "George Williams")
    private String name = "";

    @NotNull
    @NotBlank
    @ApiModelProperty(position = 2, example = "Beefway Meats ltd")
    private String company = "";

    @NotNull
    @NotBlank
    @ApiModelProperty(position = 3, example = "778-572-7853")
    private String phone = "";

    public ProviderAPI() {}

    public ProviderAPI(String name, String company, String phone) {
        this.name = name;
        this.company = company;
        this.phone = phone;
    }

    @Override
    public String toString() {
        Formatter formatter = new ValueFormatter()
                .add("name", name).add("company", company).add("phone", phone);
        return formatter.format();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
