package com.web.services.orm.entity.configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "`cash_register`")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CashRegister {

    @Id
    @ApiModelProperty(position = 1, example = "1")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cash_register_id")
    private Long id = null;

    @ApiModelProperty(position = 1, example = "Main Cash Register")
    @Column(name = "name")
    private String name = "";

    @ApiModelProperty(position = 2)
    @ManyToOne
    @JoinColumn(name = "printer_id")
    private Printer printer = null;

    public CashRegister() {}

    @Override
    public String toString() {
        Formatter formatter = new ValueFormatter()
                .add("id", id).add("name", name).add("printer", printer.getId());
        return formatter.format();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Printer getPrinter() {
        return printer;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }
}
