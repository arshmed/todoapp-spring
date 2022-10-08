package com.example.todo.service;

import com.example.todo.exception.TodoNotFoundException;
import com.example.todo.exception.UserNotFoundException;
import com.example.todo.model.Todo;
import com.example.todo.model.User;
import com.example.todo.repository.TodoRepository;
import com.example.todo.repository.UserRepository;
import com.example.todo.request.TodoCreateRequest;
import com.example.todo.request.UserCreateRequest;
import com.example.todo.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, TodoRepository todoRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public List<UserResponse> getAllUsers() {
        List<User> list = userRepository.findAll();
        return list.stream().map(UserResponse::new).collect(Collectors.toList());
    }

    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return new UserResponse(user);
    }

    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return new UserResponse(user);
    }

    public ResponseEntity<UserResponse> addUser(@Valid UserCreateRequest request) {

        if(userRepository.findByUsername(request.getUsername()).isEmpty()) {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRoles("ROLE_USER");
            user.setActive(true);
            UserResponse response = new UserResponse(user);
            userRepository.save(user);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        else return new ResponseEntity<>(HttpStatus.CONFLICT);

    }

    public UserResponse updateUser(Long userId, UserCreateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        userRepository.save(user);
        return new UserResponse(user);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.delete(user);
    }

    public List<Todo> getTodos(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return user.getTodos();
    }

    public ResponseEntity<Todo> addTodo(Long userId, TodoCreateRequest request) {

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        Todo todo = new Todo();
        todo.setUser(user);
        todo.setContent(request.getContent());

        user.getTodos().add(todo);
        userRepository.save(user);

        return new ResponseEntity<>(todo, HttpStatus.CREATED);
    }

    public void deleteTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new TodoNotFoundException(todoId));
        todoRepository.delete(todo);
    }


    public ResponseEntity<Todo> updateTodo(Long todoId, TodoCreateRequest request) {

        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new TodoNotFoundException(todoId));

        todo.setContent(request.getContent());
        todoRepository.save(todo);

        return new ResponseEntity<>(todo, HttpStatus.OK);
    }
}
