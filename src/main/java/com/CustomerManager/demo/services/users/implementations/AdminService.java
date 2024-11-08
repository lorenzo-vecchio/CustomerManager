package com.CustomerManager.demo.services.users.implementations;

import com.CustomerManager.demo.DTOs.users.CreateUserDTO;
import com.CustomerManager.demo.models.users.Admin;
import com.CustomerManager.demo.models.users.User;
import com.CustomerManager.demo.repositories.AdminRepository;
import com.CustomerManager.demo.services.users.interfaces.UserServiceCrudInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class AdminService implements UserServiceCrudInterface<Admin> {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // --------------------------------------------------------------------------------------------
    // --------------------------------------- CREATE ---------------------------------------------
    // --------------------------------------------------------------------------------------------
    @Override
    @Transactional
    public Admin create(CreateUserDTO createUserDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Admin creator = (Admin) authentication.getPrincipal();

        Admin admin = new Admin();
        admin.setCreatedBy(creator);
        admin.setFirstName(createUserDTO.getFirstName());
        admin.setLastName(createUserDTO.getLastName());
        admin.setEmail(createUserDTO.getEmail());
        admin.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        return adminRepository.save(admin);
    }

    // --------------------------------------------------------------------------------------------
    // ----------------------------------------- READ ---------------------------------------------
    // --------------------------------------------------------------------------------------------
    @Override
    @Transactional(readOnly = true)
    public Optional<Admin> read(UUID id) {
        return adminRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Admin> read(Pageable pageable) {
        return adminRepository.findAll(pageable);
    }

    // --------------------------------------------------------------------------------------------
    // --------------------------------------- UPDATE ---------------------------------------------
    // --------------------------------------------------------------------------------------------
    @Override
    @Transactional
    public Optional<Admin> update(Admin user) {
        Optional<Admin> ogAdmin = adminRepository.findById(user.getId());
        if (ogAdmin.isPresent()) {
            return Optional.of(adminRepository.save(user));
        } else {
            return Optional.empty();
        }
    }

    // --------------------------------------------------------------------------------------------
    // --------------------------------------- DELETE ---------------------------------------------
    // --------------------------------------------------------------------------------------------
    @Override
    @Transactional
    public Optional<Admin> delete(UUID id) {
        Optional<Admin> user = adminRepository.findById(id);
        user.ifPresent(admin -> adminRepository.delete(admin));
        return user;
    }
}
