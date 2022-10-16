package com.example.todo.controller;

import com.example.todo.request.UserCreateRequest;
import com.example.todo.response.UserResponse;
import com.example.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAllUsers")
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/getNumberOfUsers")
    public long getNumberOfUsers(){
        return UserService.getNumberOfUsers();
    }

    @GetMapping("/getNumberOfTodos")
    public long getNumberOfTodos(){
        return UserService.getNumberOfTodos();
    }

    @PostMapping("/addUser")
    public ResponseEntity<UserResponse> addUser(@Valid @RequestBody UserCreateRequest request){
        UserResponse response = userService.addUser(request);
        if(response != null){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/updateUser/{userId}")
    public UserResponse updateUser(@PathVariable Long userId, @Valid @RequestBody UserCreateRequest request){
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }


}
