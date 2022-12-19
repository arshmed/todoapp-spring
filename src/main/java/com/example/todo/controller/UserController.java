package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.example.todo.request.TodoCreateRequest;
import com.example.todo.response.TodoResponse;
import com.example.todo.response.UserResponse;
import com.example.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


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

    @GetMapping("/getUserTodos/{userId}")
    public List<Todo> getUserTodos(@PathVariable Long userId){
        return userService.getTodos(userId);
    }

    @PostMapping("/addTodo/{userId}")
    public ResponseEntity<TodoResponse> addTodo(@PathVariable Long userId, @RequestBody TodoCreateRequest request){
        TodoResponse response = userService.addTodo(userId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/todos/deleteTodo/{todoId}")
    public void deleteTodo(@PathVariable("todoId") Long todoId){
        userService.deleteTodo(todoId);
    }

    @PutMapping("/todos/updateTodo/{todoId}")
    public ResponseEntity<TodoResponse> updateTodo(@PathVariable Long todoId, @RequestBody TodoCreateRequest request){
        TodoResponse response = userService.updateTodo(todoId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/todos/toggleTodo/{todoId}")
    public ResponseEntity<TodoResponse> toggleTodo(@PathVariable Long todoId){
        TodoResponse response = userService.toggleTodo(todoId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
