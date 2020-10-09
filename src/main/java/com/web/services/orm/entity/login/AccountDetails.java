package com.web.services.orm.entity.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "`account`")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDetails {

    @Id
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id = null;

    @ApiModelProperty(position = 1, example = "Jouo")
    @Column(name = "username")
    private String username = "";

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    @Column(name = "password")
    private String password = "";

    @ApiModelProperty(position = 2, example = "false")
    @Column(name = "locked")
    private boolean locked = false;

    @ApiModelProperty(position = 3, example = "1603411200")
    @Column(name = "ban_time")
    private Long banTime = 0L;

    @ApiModelProperty(position = 4)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    List<AccountRole> roles = null;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    @OneToOne(mappedBy = "accountDetails")
    private UserAccount userAccount = null;

    public AccountDetails() {}

    @Override
    public String toString() {
        Formatter formatter = new ValueFormatter()
                .add("id", id).add("username", username).add("locked", locked)
                .add("banTime", banTime).add("roles", roles);
        return formatter.format();
    }

    public boolean rolesMatch(List<Long> roles) {
        List<Long> currentRoles = new ArrayList<>();

        for (AccountRole role : this.roles) {
            currentRoles.add(role.getId());
        }

        for (Long role : roles) {
            if (!currentRoles.contains(role)) { return false; }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof AccountDetails)) { return false; }
        AccountDetails that = (AccountDetails) o;
        return locked == that.locked &&
                id.equals(that.id) &&
                username.equals(that.username) &&
                password.equals(that.password) &&
                banTime.equals(that.banTime) &&
                Objects.equals(roles, that.roles) &&
                Objects.equals(userAccount, that.userAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, locked, banTime, roles, userAccount);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Long getBanTime() {
        return banTime;
    }

    public void setBanTime(Long banTime) {
        this.banTime = banTime;
    }

    public List<AccountRole> getRoles() {
        return roles;
    }

    public void setRoles(List<AccountRole> accountRole) {
        this.roles = accountRole;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}
