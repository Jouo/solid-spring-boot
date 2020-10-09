package com.web.services.orm.entity.business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.services.orm.entity.configuration.Category;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "product")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    @Id
    @ApiModelProperty(position = 1, example = "1")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id = null;

    @ApiModelProperty(position = 2, example = "Soda")
    @Column(name = "name")
    private String name = "";

    @ApiModelProperty(position = 3, example = "1")
    @Column(name = "cost")
    private Float cost = 0.0f;

    @ApiModelProperty(position = 4, example = "2")
    @Column(name = "price")
    private Float price = 0.0f;

    @ApiModelProperty(position = 5, example = "/beverages/soda.jpeg")
    @Column(name = "image")
    private String image = "";

    @ApiModelProperty(position = 6)
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "stock_id")
    private Stock stock = null;

    @ApiModelProperty(position = 7)
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category = null;

    public Product() {}

    @Override
    public String toString() {
        Formatter formatter = new ValueFormatter()
                .add("id", id).add("name", name).add("cost", cost).add("price", price)
                .add("image", image).add("stock", stock.getId()).add("category", category.getId());
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

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
