package com.web.services.orm.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.services.utility.general.classes.ValueFormatter;
import com.web.services.utility.general.interfaces.Formatter;
import com.web.services.validator.intercaces.MustExistList;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAPI {

    @NotNull
    @NotBlank
    @ApiModelProperty(position = 1, example = "Jashua")
    private String name = "";

    @NotNull
    @NotBlank
    @ApiModelProperty(position = 2, example = "Developer")
    private String lastName = "";

    @NotNull
    @NotBlank
    @ApiModelProperty(position = 3, example = "jouo")
    private String username = null;

    @NotNull
    @NotBlank
    @ApiModelProperty(position = 4, example = "password")
    private String password = null;

    @NotNull
    @NotEmpty
    @MustExistList(className = "AccountRole", message = "One or more roles don't exist")
    @ApiModelProperty(position = 5, example = "[2]", dataType = "List")
    private List<Long> roles = null;

    public UserAPI() {}

    public UserAPI(String name, String lastName, String username, String password, List<Long> roles) {
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public String toString() {
        Formatter formatter = new ValueFormatter()
                .add("name", name).add("last name", lastName)
                .add("username", username).add("roles", roles);
        return formatter.format();
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

    public List<Long> getRoles() {
        return roles;
    }

    public void setRoles(List<Long> roles) {
        this.roles = roles;
    }
}
