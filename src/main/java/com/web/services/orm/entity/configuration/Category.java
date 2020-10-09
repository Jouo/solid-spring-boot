package com.web.services.orm.entity.configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "`category`")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {

    @Id
    @ApiModelProperty(position = 1, example = "1")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @ApiModelProperty(position = 2, example = "Beverage")
    @Column(name = "name")
    private String name = "";

    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        Formatter formatter = new ValueFormatter()
                .add("id", id).add("name", name);
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
}
