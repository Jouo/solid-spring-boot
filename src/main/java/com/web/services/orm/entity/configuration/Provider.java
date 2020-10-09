package com.web.services.orm.entity.configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "`provider`")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Provider {

    @Id
    @ApiModelProperty(position = 1, example = "1")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provider_id")
    private Long id = null;

    @ApiModelProperty(position = 2, example = "George Williams")
    @Column(name = "name")
    private String name = "";

    @ApiModelProperty(position = 3, example = "Beefway Meats ltd")
    @Column(name = "company")
    private String company = "";

    @ApiModelProperty(position = 4, example = "778-512-5719")
    @Column(name = "phone")
    private String phone = "";

    public Provider() {}

    @Override
    public String toString() {
        Formatter formatter = new ValueFormatter()
                .add("id", id).add("name", name)
                .add("company", company).add("phone", phone);
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
