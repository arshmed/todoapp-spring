package com.example.todo.service;


import com.example.todo.model.ConfirmUserToken;
import com.example.todo.model.User;

public interface ConfirmUserService {
    ConfirmUserToken findByToken(String token);
    ConfirmUserToken createToken(User user);
    void delete(ConfirmUserToken confirmUserToken);

}
