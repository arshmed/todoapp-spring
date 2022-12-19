package com.example.todo.response;

import com.example.todo.model.Todo;
import com.example.todo.model.User;

import java.util.List;

public class UserResponse {

    private String username;
    private String role;
    private List<Todo> todos;

    public UserResponse(User user){
        this.username=user.getUsername();
        this.role= user.getRoles().getRoleName().contains("ROLE_ADMIN") ? "Admin" : "User";
        this.todos=user.getTodos();
    }

    public UserResponse() {
    }

    public UserResponse(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
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
