package com.example.todo.service;

import com.example.todo.exception.TodoNotFoundException;
import com.example.todo.exception.UserNotFoundException;
import com.example.todo.model.Todo;
import com.example.todo.model.User;
import com.example.todo.repository.TodoRepository;
import com.example.todo.repository.UserRepository;
import com.example.todo.request.TodoCreateRequest;
import com.example.todo.request.UserCreateRequest;
import com.example.todo.response.TodoResponse;
import com.example.todo.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;

    private static long numberOfUsers=0;
    private static long numberOfTodos=0;

    @Autowired
    public UserService(UserRepository userRepository, TodoRepository todoRepository, PasswordEncoder passwordEncoder, EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSenderService = emailSenderService;
    }


    public List<UserResponse> getAllUsers() {
        List<User> list = userRepository.findAll();
        return list.stream().map(UserResponse::new).collect(Collectors.toList());
    }



    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return new UserResponse(user);
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        return user;
    }

    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if(user!=null)
        return new UserResponse(user);
        else return null;
    }

    public UserResponse addUser(@Valid @RequestBody UserCreateRequest request) {

        if(userRepository.findByUsername(request.getUsername()).isEmpty()) {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setEmail(request.getEmail());
            user.setRoles("ROLE_USER");
            user.setActive(false);
            UserResponse response = new UserResponse(user);
            userRepository.save(user);
            numberOfUsers++;
            emailSenderService.sendEmail(user);
            return response;
        }
        else
            return null;
    }

    public User updateUser(User user){
        return userRepository.save(user);
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
        numberOfUsers--;
    }

    public List<Todo> getTodos(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return user.getTodos();
    }

    public TodoResponse addTodo(Long userId, TodoCreateRequest request) {

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: "+userId));

        Todo todo = new Todo();
        todo.setUser(user);
        todo.setContent(request.getContent());

        user.getTodos().add(todo);
        userRepository.save(user);
        numberOfTodos++;

        return new TodoResponse(todo);
    }

    public void deleteTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new TodoNotFoundException(todoId));
        todoRepository.delete(todo);
        numberOfTodos--;
    }


    public TodoResponse updateTodo(Long todoId, TodoCreateRequest request) {

        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new TodoNotFoundException(todoId));

        todo.setContent(request.getContent());
        todoRepository.save(todo);

        return new TodoResponse(todo);
    }

    public TodoResponse toggleTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new TodoNotFoundException(todoId));
        todo.setCompleted(!todo.isCompleted());
        todoRepository.save(todo);
        return new TodoResponse(todo);
    }

    public static long getNumberOfUsers() {
        return numberOfUsers;
    }

    public static long getNumberOfTodos(){return numberOfTodos;}
}
