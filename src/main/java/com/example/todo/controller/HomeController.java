package com.example.todo.controller;

import com.example.todo.request.UserCreateRequest;
import com.example.todo.response.UserResponse;
import com.example.todo.service.HomeService;
import com.example.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class HomeController {

    private final UserService userService;
    private final HomeService homeService;

    @Autowired
    public HomeController(UserService userService, HomeService homeService) {
        this.userService = userService;
        this.homeService = homeService;
    }

    @GetMapping("/home")
    public String home(){
        return ("<h1>Welcome</h1>");
    }

    @GetMapping("/login")
    public String login(){
        return "Login Page";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserCreateRequest request){
        String token = homeService.login(request);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/register")
    public String homeRegister(){
        return ("<h1>For registration click the button</h1>");
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserCreateRequest request){
        UserResponse response = userService.addUser(request);
        if(response == null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
