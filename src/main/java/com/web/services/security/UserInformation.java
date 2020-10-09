package com.web.services.security;

import com.web.services.orm.entity.login.AccountDetails;
import com.web.services.orm.entity.login.AccountRole;
import com.web.services.orm.entity.login.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserInformation implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private boolean locked;
    private Long banTime;
    private UserAccount userAccount;
    private Collection<GrantedAuthority> authorities;

    public UserInformation(UserAccount userAccount) {
        this.id = userAccount.getId();
        this.username = userAccount.getAccountDetails().getUsername();
        this.password = userAccount.getAccountDetails().getPassword();
        this.locked = userAccount.getAccountDetails().isLocked();
        this.banTime = userAccount.getAccountDetails().getBanTime();
        this.userAccount = userAccount;
        this.authorities = buildAuthorities(userAccount);
    }

    private Collection<GrantedAuthority> buildAuthorities(UserAccount userAccount) {
        AccountDetails accountDetails = userAccount.getAccountDetails();
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        for (AccountRole role : accountDetails.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public Long getBanTime() {
        return banTime;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return (banTime == 0L);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
