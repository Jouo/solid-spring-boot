package com.web.services.orm.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigurationAPI {

    @NotBlank
    @NotNull
    @ApiModelProperty(position = 1, example = "Lilstore")
    private String company = "";

    @NotBlank
    @NotNull
    @ApiModelProperty(position = 2, example = "Vancouver, BC")
    private String address = "";

    @NotBlank
    @NotNull
    @ApiModelProperty(position = 3, example = "778-882-8291")
    private String phone = "";

    @NotBlank
    @NotNull
    @ApiModelProperty(position = 4, example = "¡Thanks for shopping!")
    private String message = "";

    public ConfigurationAPI() {}

    public ConfigurationAPI(String company, String address, String phone, String message) {
        this.company = company;
        this.address = address;
        this.phone = phone;
        this.message = message;
    }

    @Override
    public String toString() {
        Formatter formatter = new ValueFormatter()
                .add("company", company).add("address", address)
                .add("phone", phone).add("message", message);
        return formatter.format();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
