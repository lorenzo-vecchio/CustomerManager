package com.CustomerManager.demo.services.users.implementations;

import com.CustomerManager.demo.DTOs.users.CreateUserDTO;
import com.CustomerManager.demo.models.users.Admin;
import com.CustomerManager.demo.models.users.Professional;
import com.CustomerManager.demo.repositories.ProfessionalRepository;
import com.CustomerManager.demo.services.users.interfaces.UserServiceCrudInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProfessionalService implements UserServiceCrudInterface<Professional> {

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // --------------------------------------------------------------------------------------------
    // --------------------------------------- CREATE ---------------------------------------------
    // --------------------------------------------------------------------------------------------
    @Override
    @Transactional
    public Professional create(CreateUserDTO createUserDTO) {
        Professional professional = new Professional();
        professional.setFirstName(createUserDTO.getFirstName());
        professional.setLastName(createUserDTO.getLastName());
        professional.setEmail(createUserDTO.getEmail());
        professional.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        return professionalRepository.save(professional);
    }

    // --------------------------------------------------------------------------------------------
    // ----------------------------------------- READ ---------------------------------------------
    // --------------------------------------------------------------------------------------------
    @Override
    @Transactional(readOnly = true)
    public Optional<Professional> read(UUID id) {
        return professionalRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Professional> read(Pageable pageable) {
        return professionalRepository.findAll(pageable);
    }

    // --------------------------------------------------------------------------------------------
    // --------------------------------------- UPDATE ---------------------------------------------
    // --------------------------------------------------------------------------------------------
    @Override
    @Transactional
    public Optional<Professional> update(Professional user) {
        Optional<Professional> ogAdmin = professionalRepository.findById(user.getId());
        if (ogAdmin.isPresent()) {
            return Optional.of(professionalRepository.save(user));
        } else {
            return Optional.empty();
        }
    }

    // --------------------------------------------------------------------------------------------
    // --------------------------------------- DELETE ---------------------------------------------
    // --------------------------------------------------------------------------------------------
    @Override
    @Transactional
    public Optional<Professional> delete(UUID id) {
        Optional<Professional> user = professionalRepository.findById(id);
        user.ifPresent(professional -> professionalRepository.delete(professional));
        return user;
    }
}
