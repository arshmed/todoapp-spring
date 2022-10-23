package com.example.todo.model;

import lombok.Data;

@Data
public class BaseResponseModel {
    private int responseCode;
    private String responseMessage;
}
