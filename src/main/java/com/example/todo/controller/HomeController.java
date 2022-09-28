package com.example.todo.controller;

import com.example.todo.model.User;
import com.example.todo.request.UserCreateRequest;
import com.example.todo.response.UserResponse;
import com.example.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HomeController {

    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(){
        return ("<h1>Welcome</h1>");
    }

    @GetMapping("/user")
    public String homeUser(){return ("<h1>Welcome User</h1>");}

    @GetMapping("/register")
    public String homeRegister(){
        return ("<h1>For registration click the button</h1>");
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserCreateRequest request){
        return userService.addUser(request);
    }


}
