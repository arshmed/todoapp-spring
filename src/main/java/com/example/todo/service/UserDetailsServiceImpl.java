package com.example.todo.service;

import com.example.todo.exception.UserNotFoundException;
import com.example.todo.model.JwtUserDetails;
import com.example.todo.model.User;
import com.example.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(username + " not found"));

        //return user.map(JwtUserDetails::new).get();

        return new JwtUserDetails(user);

    }

    public UserDetails loadUserByUserId(Long id) {

        User user = userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException(id + " not found"));
        return new JwtUserDetails(user);

    }
}
