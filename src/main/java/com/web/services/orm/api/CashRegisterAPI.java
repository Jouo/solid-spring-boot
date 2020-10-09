package com.web.services.orm.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import com.web.services.validator.intercaces.MustExist;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CashRegisterAPI {

    @NotNull
    @NotBlank
    @ApiModelProperty(position = 1, example = "Main Cash Register")
    private String name = "";

    @NotNull
    @MustExist(className = "Printer", message = "Printer doesn't exist")
    @ApiModelProperty(position = 2, example = "1")
    private Long printer = null;

    public CashRegisterAPI() {}

    public CashRegisterAPI(String name, Long printer) {
        this.name = name;
        this.printer = printer;
    }

    @Override
    public String toString() {
        Formatter formatter = new ValueFormatter()
                .add("name", name).add("printer", printer);
        return formatter.format();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrinter() {
        return printer;
    }

    public void setPrinter(Long printer) {
        this.printer = printer;
    }
}
