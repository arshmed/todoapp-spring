package com.example.todo.response;


import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.List;


public class ErrorHandler {

    private final Errors errors;

    public ErrorHandler(Errors errors){
        this.errors=errors;
    }

    public void controlOfErrors(){

        if (errors.hasErrors()){
            List<ObjectError> errorList = errors.getAllErrors();

            if(errors.hasFieldErrors()){
                System.out.println(errors.getFieldError());
            }

            System.out.println(errors.getAllErrors());

        }

    }


}
