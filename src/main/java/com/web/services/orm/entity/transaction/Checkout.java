package com.web.services.orm.entity.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.services.Utility;
import com.web.services.orm.entity.login.UserAccount;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "`checkout`")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Checkout {

    @Id
    @ApiModelProperty(position = 1, example = "1")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checkout_id")
    private Long id = null;

    @ApiModelProperty(position = 2, example = "1585183589")
    @Column(name = "date")
    private Long date = Instant.now().getEpochSecond();

    @ApiModelProperty(position = 3, example = "750")
    @Column(name = "sales")
    private Float totalSale = 0.0f;

    @ApiModelProperty(position = 4, example = "250")
    @Column(name = "purchases")
    private Float totalPurchase = 0.0f;

    @ManyToOne
    @ApiModelProperty(position = 5)
    @JoinColumn(name = "user_id")
    private UserAccount userAccount = null;

    @ApiModelProperty(position = 6)
    @OneToMany(mappedBy = "checkout")
    private List<Purchase> purchases = null;

    @ApiModelProperty(position = 7)
    @OneToMany(mappedBy = "checkout")
    private List<Sale> sales = null;

    public Checkout() {}

    public Checkout(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public String toString() {
        String date = Utility.dateFormat(this.date);
        Formatter formatter = new ValueFormatter()
                .add("id", id).add("date", date).add("total sales", totalSale)
                .add("total purchases", totalPurchase).add("user", userAccount);
        return formatter.format();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Float getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(Float totalSale) {
        this.totalSale = totalSale;
    }

    public Float getTotalPurchase() {
        return totalPurchase;
    }

    public void setTotalPurchase(Float totalPurchase) {
        this.totalPurchase = totalPurchase;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        if (purchases != null) {
            this.purchases = purchases;
            for (Purchase purchase : this.purchases) {
                purchase.setCheckout(this);
            }
        }
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        if (sales != null) {
            this.sales = sales;
            for (Sale sale : this.sales) {
                sale.setCheckout(this);
            }
        }
    }
}
