package com.web.services.orm.entity.configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "`configuration`")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Configuration {

    @Id
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "configuration_id")
    private Long id = null;

    @ApiModelProperty(position = 1, example = "Lilstore")
    @Column(name = "company")
    private String company = "";

    @ApiModelProperty(position = 2, example = "Vancouver, BC")
    @Column(name = "address")
    private String address = "";

    @ApiModelProperty(position = 3, example = "778-882-8291")
    @Column(name = "phone")
    private String phone = "";

    @ApiModelProperty(position = 4, example = "¡Thanks for shopping!")
    @Column(name = "message")
    private String message = "";

    public Configuration() {}

    @Override
    public String toString() {
        Formatter formatter = new ValueFormatter()
                .add("id", id).add("company", company).add("address", address)
                .add("phone", phone).add("message", message);
        return formatter.format();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
