package com.example.todo.request;

import com.sun.istack.NotNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserLoginRequest {

    @NotNull
    @NotEmpty(message = "username may not be empty")
    private String username;

    @NotNull
    @NotEmpty(message = "password may not be empty")
    @Size(min = 5, max = 20, message = "password should be between 5 and 20 chars in length")
    @NotBlank
    private String password;

    public UserLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserLoginRequest() {
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
