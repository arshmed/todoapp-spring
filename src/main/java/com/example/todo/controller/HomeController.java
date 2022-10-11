package com.example.todo.controller;

import com.example.todo.request.UserCreateRequest;
import com.example.todo.response.UserResponse;
import com.example.todo.security.JwtTokenProvider;
import com.example.todo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class HomeController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public HomeController(UserService userService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager=authenticationManager;
        this.jwtTokenProvider=jwtTokenProvider;
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

        if(userService.getUserByUsername(request.getUsername()) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        Authentication auth =  authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);

        return new ResponseEntity<>("Bearer " + jwtToken, HttpStatus.OK);
    }

    @GetMapping("/register")
    public String homeRegister(){
        return ("<h1>For registration click the button</h1>");
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserCreateRequest request){
        UserResponse response = userService.addUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
