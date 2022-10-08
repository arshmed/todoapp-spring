package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.example.todo.model.User;
import com.example.todo.request.TodoCreateRequest;
import com.example.todo.request.UserCreateRequest;
import com.example.todo.response.UserResponse;
import com.example.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String homeAdmin(){return ("<h1>Welcome Admin</h1>");}

    @GetMapping("/getAllUsers")
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/getUserWithId/{userId}")
    public UserResponse getUserById(@PathVariable Long userId){
        return userService.getUserById(userId);
    }

    @GetMapping("/getUserByUsername")
    public UserResponse getUserByUsername(@RequestParam String username){
        return userService.getUserByUsername(username);
    }

    @PostMapping("/addUser")
    public ResponseEntity<UserResponse> addUser(@Valid @RequestBody UserCreateRequest request){
        return userService.addUser(request);
    }

    @PutMapping("/updateUser/{userId}")
    public UserResponse updateUser(@PathVariable Long userId, @Valid @RequestBody UserCreateRequest request){
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }

    @GetMapping("/getUserTodos/{userId}")
    public List<Todo> getUserTodos(@PathVariable Long userId){
        return userService.getTodos(userId);
    }


    @PostMapping("/addTodo/{userId}")
    public ResponseEntity<Todo> addTodo(@PathVariable Long userId, TodoCreateRequest request){
        return userService.addTodo(userId, request);
    }

    @DeleteMapping("/todos/deleteTodo/{todoId}")
    public void deleteTodo(@PathVariable("todoId") Long todoId){
        userService.deleteTodo(todoId);
    }

    @PutMapping("/todos/updateTodo/{todoId}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long todoId, @RequestBody TodoCreateRequest request){
        return userService.updateTodo(todoId, request);
    }

}
