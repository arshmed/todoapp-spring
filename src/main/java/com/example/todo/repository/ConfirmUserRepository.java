package com.example.todo.repository;


import com.example.todo.model.ConfirmUserToken;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConfirmUserRepository extends CrudRepository<ConfirmUserToken,Long> {
    ConfirmUserToken findByToken(String token);
}
