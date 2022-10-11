package com.example.todo.response;

import com.example.todo.model.Todo;

public class TodoResponse {

    private String content;
    private boolean completed;

    public TodoResponse(Todo todo){
        this.content=todo.getContent();
        this.completed=todo.isCompleted();
    }

    public TodoResponse() {
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
