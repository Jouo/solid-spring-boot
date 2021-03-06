package com.web.services.orm.entity.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.services.Utility;
import com.web.services.orm.entity.configuration.CashRegister;
import com.web.services.orm.entity.login.UserAccount;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "`sale`")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sale {

    @Id
    @ApiModelProperty(position = 1, example = "1")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Long id;

    @ApiModelProperty(position = 2, example = "1584413167")
    @Column(name = "date")
    private Long date = Instant.now().getEpochSecond();

    @ApiModelProperty(position = 3, example = "250")
    @Column(name = "total")
    private Float total = 0.0f;

    @ApiModelProperty(position = 4)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount userAccount = null;

    @ApiModelProperty(position = 5)
    @ManyToOne
    @JoinColumn(name = "cash_register_id")
    private CashRegister cashRegister = null;

    @ApiModelProperty(position = 6)
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<Order> orders = null;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    @ManyToOne
    @JoinColumn(name = "checkout_id")
    private Checkout checkout = null;

    public Sale() {}

    @Override
    public String toString() {
        String date = Utility.dateFormat(this.date);
        Formatter formatter = new ValueFormatter()
                .add("id", id).add("date", date).add("total", total)
                .add("user", userAccount.getId()).add("cash register", cashRegister.getId())
                .add("orders", orders);
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

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public CashRegister getCashRegister() {
        return cashRegister;
    }

    public void setCashRegister(CashRegister cashRegister) {
        this.cashRegister = cashRegister;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Checkout getCheckout() {
        return checkout;
    }

    public void setCheckout(Checkout checkout) {
        this.checkout = checkout;
    }
}
