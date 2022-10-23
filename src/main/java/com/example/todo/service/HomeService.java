package com.example.todo.service;

import com.example.todo.exception.UserNotFoundException;
import com.example.todo.model.User;
import com.example.todo.request.UserCreateRequest;
import com.example.todo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;

@Service
public class HomeService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public HomeService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }


    @PostMapping
    public String login(@RequestBody @Valid UserCreateRequest request) {
        User user = userService.getUserByEmail(request.getEmail());

        if(userService.getUserByUsername(request.getUsername()) == null)
            throw new UserNotFoundException(request.getUsername() + " not found");

        if(!user.isActive()){
            return "User is  not active. Please activate your account by checking your email!";
        }

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        Authentication auth =  authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);

        return "Bearer " + jwtToken;

    }


}
