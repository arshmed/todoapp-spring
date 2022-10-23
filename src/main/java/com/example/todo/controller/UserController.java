package com.example.todo.controller;

import com.example.todo.model.BaseResponseModel;
import com.example.todo.model.ConfirmUserToken;
import com.example.todo.model.Todo;
import com.example.todo.model.User;
import com.example.todo.request.TodoCreateRequest;
import com.example.todo.request.UserCreateRequest;
import com.example.todo.response.TodoResponse;
import com.example.todo.response.UserResponse;
import com.example.todo.service.ConfirmUserService;
import com.example.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ConfirmUserService confirmUserService;


    @Autowired
    public UserController(UserService userService, ConfirmUserService confirmUserService) {
        this.userService = userService;
        this.confirmUserService = confirmUserService;
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

    @GetMapping("/confirm-email")
    public ResponseEntity<BaseResponseModel> confirmUserEmail(@RequestParam("uuid") String uuid) {

        BaseResponseModel responseModel=new BaseResponseModel();

        try {
            ConfirmUserToken confirmUserToken=confirmUserService.findByToken(uuid);
            return checkByConfirmUserToken(responseModel, confirmUserToken);
        }catch (Exception e) {
            responseModel.setResponseCode(500);
            responseModel.setResponseMessage("System Error...");
            return new ResponseEntity<>(responseModel, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    private ResponseEntity<BaseResponseModel> checkByConfirmUserToken(BaseResponseModel responseModel, ConfirmUserToken confirmUserToken) {
        if(confirmUserToken==null) {
            responseModel.setResponseCode(400);
            responseModel.setResponseMessage("Thanks for confirming your email, but your confirmation code is invalid");
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        }else if(confirmUserToken.isExpired()){
            responseModel.setResponseCode(400);
            responseModel.setResponseMessage("Thanks for confirming your email, but your confirmation code has expired");
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        }else {
            try {
                User user=userService.getUserByEmail(confirmUserToken.getUser().getEmail());
                return checkUserByConfirmationToken(responseModel, user);
            }catch(Exception e) {
                responseModel.setResponseCode(500);
                responseModel.setResponseMessage("System Error...");
                return new ResponseEntity<>(responseModel, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    private ResponseEntity<BaseResponseModel> checkUserByConfirmationToken(BaseResponseModel responseModel, User user) {
        if(user==null) {
            responseModel.setResponseCode(400);
            responseModel.setResponseMessage("Thanks for confirming your email, but your confirmation code is invalid");
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        }else {
            user.setActive(true);
            userService.updateUser(user);
            responseModel.setResponseCode(200);
            responseModel.setResponseMessage("Your confirmation is successful. You can now login.");
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }
    }

}
