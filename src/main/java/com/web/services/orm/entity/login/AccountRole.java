package com.web.services.orm.entity.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "`role`")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountRole {

    @Id
    @ApiModelProperty(position = 1, example = "1")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id = null;

    @ApiModelProperty(position = 2, example = "Admin")
    @Column(name = "description")
    private String description = "";

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    @Column(name = "role")
    private String role = "";

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    @ManyToMany
    @JoinTable(name = "account_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id"))
    Set<AccountDetails> accountDetails = new LinkedHashSet<>();

    public AccountRole() {}

    @Override
    public String toString() {
        Formatter formatter = new ValueFormatter()
                .add("id", id).add("description", description);
        return formatter.format();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<AccountDetails> getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(Set<AccountDetails> accountDetails) {
        this.accountDetails = accountDetails;
    }
}
