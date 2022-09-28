package com.example.todo.request;

import com.sun.istack.NotNull;

import javax.validation.constraints.NotEmpty;

public class TodoCreateRequest {

    @NotNull
    @NotEmpty(message = "Content may not be empty")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
