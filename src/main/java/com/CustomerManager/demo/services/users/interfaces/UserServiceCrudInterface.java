package com.CustomerManager.demo.services.users.interfaces;

import com.CustomerManager.demo.DTOs.users.CreateUserDTO;
import com.CustomerManager.demo.models.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface UserServiceCrudInterface<T extends User> {
    public T create(CreateUserDTO createUserDTO);
    public Optional<T> read(UUID id);
    public Page<T> read(Pageable pageable);
    public Optional<T> update(T user);
    public Optional<T> delete(UUID id);
}
