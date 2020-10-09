package com.web.services.orm.entity.configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "`printer`")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Printer {

    @Id
    @ApiModelProperty(position = 1, example = "1")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "printer_id")
    private Long id;

    @ApiModelProperty(position = 2, example = "Main Printer")
    @Column(name = "name")
    private String name = "";

    @ApiModelProperty(position = 3, example = "10.0.0.5")
    @Column(name = "ip")
    private String ip = "";

    public Printer() {}

    @Override
    public String toString() {
        Formatter formatter = new ValueFormatter()
                .add("id", id).add("name", name).add("ip", ip);
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
