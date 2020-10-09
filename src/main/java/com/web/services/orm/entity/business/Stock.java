package com.web.services.orm.entity.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "`stock`")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stock {

    @Id
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long id;

    @ApiModelProperty(position = 1, example = "20")
    @Column(name = "quantity")
    private Integer quantity = 0;

    public Stock() {}

    @Override
    public String toString() {
        Formatter formatter = new ValueFormatter()
                .add("id", id).add("quantity", quantity);
        return formatter.format();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer currentQuantity) {
        this.quantity = currentQuantity;
    }
}
