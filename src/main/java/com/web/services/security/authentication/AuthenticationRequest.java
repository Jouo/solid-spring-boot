package com.web.services.security.authentication;

import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AuthenticationRequest {

    @NotNull
    @NotBlank
    @ApiModelProperty(position = 1, example = "Jashua")
    private String username;

    @NotNull
    @NotBlank
    @ApiModelProperty(position = 2, example = "mypassword")
    private String password;

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        Formatter formatter = new ValueFormatter()
                .add("username", username)
                .add("password", "HIDEN");
        return formatter.format();
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
}
