package com.example.todo.exception;

public class TodoNotFoundException extends RuntimeException{

    public TodoNotFoundException(Long todoId){
        super("Todo with id " + todoId + " not found");
    }

}
