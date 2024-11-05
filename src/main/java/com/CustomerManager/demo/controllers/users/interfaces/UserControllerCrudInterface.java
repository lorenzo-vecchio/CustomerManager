package com.CustomerManager.demo.controllers.users.interfaces;

import com.CustomerManager.demo.DTOs.users.CreateUserDTO;
import com.CustomerManager.demo.models.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface UserControllerCrudInterface<T extends User> {
    ResponseEntity<T> create(CreateUserDTO createUserDTO);
    ResponseEntity<Page<T>> read(Pageable pageable);
    ResponseEntity<T> read(UUID id);
    ResponseEntity<T> update(T user);
    ResponseEntity<T> delete(UUID id);
}
