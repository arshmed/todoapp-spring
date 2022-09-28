package com.example.todo.response;

import com.example.todo.model.User;

public class UserResponse {

    private String username;
    private String role;

    public UserResponse(User user){
        this.username=user.getUsername();
        this.role= user.getRoles().contains("ROLE_ADMIN") ? "Admin" : "User";
    }

    public UserResponse() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
