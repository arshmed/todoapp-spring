package com.example.todo.controller;

import com.example.todo.request.UserCreateRequest;
import com.example.todo.response.UserResponse;
import com.example.todo.service.HomeService;
import com.example.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class HomeController {

    private final UserService userService;
    private final HomeService homeService;

    @Autowired
    public HomeController(UserService userService, HomeService homeService) {
        this.userService = userService;
        this.homeService = homeService;
    }


    @GetMapping
    public String home(){
        return ("<h1>Welcome</h1>");
    }


    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestBody UserCreateRequest request){
        String token = homeService.login(request);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/auth/register")
    public String homeRegister(){
        return ("<h1>For registration click the button</h1>");
    }

    @PostMapping("/auth/register")
    public ResponseEntity<Object> register(@Valid @RequestBody UserCreateRequest request, Errors errors){
        // TODO: 23.10.2022
                // set error messages, create a class and collect all errors.
        if(errors.hasErrors()){
            return new ResponseEntity<>(errors.getFieldError().getDefaultMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        UserResponse response = userService.addUser(request);
        if(response == null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        else
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
