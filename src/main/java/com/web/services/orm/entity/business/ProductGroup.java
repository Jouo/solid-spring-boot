package com.web.services.orm.entity.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.services.Utility;
import com.web.services.utility.general.classes.ValueFormatter;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "`group`")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductGroup {

    @Id
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id = null;

    @ApiModelProperty(position = 1, example = "2")
    @Column(name = "quantity")
    private Integer quantity = 0;

    @ApiModelProperty(position = 2)
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product = null;

    public ProductGroup() {}

    public ProductGroup(Integer quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    @Override
    public String toString() {
        return Utility.arrayFormat(product.getId(), quantity);
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

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
