package com.web.services.orm.entity.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.services.Utility;
import com.web.services.orm.entity.business.Product;
import com.web.services.utility.general.classes.ValueFormatter;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "`order`")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    @Id
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id = null;

    @ApiModelProperty(position = 1, example = "2")
    @Column(name = "quantity")
    private Integer quantity = 0;

    @ApiModelProperty(position = 2, example = "100")
    @Column(name = "total")
    private Float total = 0.0f;

    @ApiModelProperty(position = 3)
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product = null;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;

    public Order() {}

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

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }
}
