package com.web.services.orm.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PrinterAPI {

    @NotNull
    @NotBlank
    @ApiModelProperty(position = 1, example = "Main Printer")
    private String name = "";

    @NotNull
    @NotBlank
    @ApiModelProperty(position = 2, example = "10.0.0.5")
    private String ip = "";

    public PrinterAPI() {}

    public PrinterAPI(String name, String ip) {
        this.name = name;
        this.ip = ip;
    }

    @Override
    public String toString() {
        Formatter formatter = new ValueFormatter()
                .add("name", name).add("ip", ip);
        return formatter.format();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
