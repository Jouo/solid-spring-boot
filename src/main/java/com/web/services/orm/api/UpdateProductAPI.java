package com.web.services.orm.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import com.web.services.validator.intercaces.MustExist;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateProductAPI {

    @NotNull
    @NotBlank
    @ApiModelProperty(position = 1, example = "Soda")
    private String name = "";

    @NotNull
    @Min(value = 0)
    @ApiModelProperty(position = 2, example = "1")
    private Float cost = 0.0f;

    @NotNull
    @Min(value = 0)
    @ApiModelProperty(position = 3, example = "2")
    private Float price = 0.0f;

    @NotNull
    @NotBlank
    @ApiModelProperty(position = 4, example = "/beverages/soda.jpg")
    private String image = "";

    @NotNull
    @MustExist(className = "Category", message = "Category doesn't exist")
    @ApiModelProperty(position = 5, example = "1")
    private Long category = null;

    public UpdateProductAPI() {}

    public UpdateProductAPI(String name, Float cost, Float price, String image, Long category) {
        this.name = name;
        this.cost = cost;
        this.price = price;
        this.image = image;
        this.category = category;
    }

    @Override
    public String toString() {
        Formatter formatter = new ValueFormatter()
                .add("name", name).add("cost", cost).add("price", price)
                .add("image", image).add("category", category);
        return formatter.format();
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

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }
}
