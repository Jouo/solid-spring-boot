package com.web.services.security.authentication;

import com.web.services.orm.entity.login.UserAccount;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import io.swagger.annotations.ApiModelProperty;

public class AuthenticationResponse {

    @ApiModelProperty(position = 1, example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb3Vv...")
    private String token;

    @ApiModelProperty(position = 2)
    private UserAccount user;

    public AuthenticationResponse(String token, UserAccount user) {
        this.token = token;
        this.user = user;
    }

    @Override
    public String toString() {
        Formatter formatter = new ValueFormatter()
                .add("token", "HIDEN")
                .add("user", user.getId());
        return formatter.format();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }
}
