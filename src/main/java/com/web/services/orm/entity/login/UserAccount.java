package com.web.services.orm.entity.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "`user`")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAccount {

    @Id
    @ApiModelProperty(position = 1, example = "1")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id = null;

    @ApiModelProperty(position = 2, example = "Jashua")
    @Column(name = "name")
    private String name = "";

    @ApiModelProperty(position = 3, example = "Developer")
    @Column(name = "last_name")
    private String lastName = "";

    @ApiModelProperty(position = 4)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private AccountDetails accountDetails = null;

    public UserAccount() {}

    public UserAccount(AccountDetails accountDetails) {
        this.accountDetails = accountDetails;
    }

    @Override
    public String toString() {
        Formatter formatter = new ValueFormatter()
                .add("id", id).add("name", name).add("last name", lastName)
                .add("account", accountDetails);
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public AccountDetails getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(AccountDetails accountDetails) {
        this.accountDetails = accountDetails;
    }
}
