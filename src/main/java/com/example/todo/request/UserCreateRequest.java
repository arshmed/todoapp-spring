package com.example.todo.request;

import com.example.todo.model.User;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserCreateRequest {


    @NotNull
    @NotEmpty(message = "username may not be empty")
    private String username;

    @NotNull
    @NotEmpty(message = "password may not be empty")
    @Size(min = 5, max = 20, message = "password should be between 5 and 20 chars in length")
    @NotBlank
    private String password;
    @Email
    @NotNull(message = "Email may not be null")
    private String email;

    public UserCreateRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email=email;
    }


    public UserCreateRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
