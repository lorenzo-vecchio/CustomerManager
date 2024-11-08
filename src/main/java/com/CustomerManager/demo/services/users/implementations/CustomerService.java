package com.CustomerManager.demo.services.users.implementations;

import com.CustomerManager.demo.DTOs.users.CreateUserDTO;
import com.CustomerManager.demo.models.users.Customer;
import com.CustomerManager.demo.models.users.Professional;
import com.CustomerManager.demo.repositories.CustomerRepository;
import com.CustomerManager.demo.services.users.interfaces.UserServiceCrudInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService implements UserServiceCrudInterface<Customer> {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // --------------------------------------------------------------------------------------------
    // --------------------------------------- CREATE ---------------------------------------------
    // --------------------------------------------------------------------------------------------
    @Override
    @Transactional
    public Customer create(CreateUserDTO createUserDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Professional> professionals = new ArrayList<>();
        professionals.add((Professional) authentication.getPrincipal());

        Customer customer = new Customer();
        customer.setProfessionals(professionals);
        customer.setFirstName(createUserDTO.getFirstName());
        customer.setLastName(createUserDTO.getLastName());
        customer.setEmail(createUserDTO.getEmail());
        customer.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        return customerRepository.save(customer);
    }

    // --------------------------------------------------------------------------------------------
    // ----------------------------------------- READ ---------------------------------------------
    // --------------------------------------------------------------------------------------------
    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> read(UUID id) {
        return customerRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Customer> read(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Professional> professionals = new ArrayList<>();
        professionals.add((Professional) authentication.getPrincipal());
        return customerRepository.findCustomersByProfessionals(professionals, pageable);
    }

    // --------------------------------------------------------------------------------------------
    // --------------------------------------- UPDATE ---------------------------------------------
    // --------------------------------------------------------------------------------------------
    @Override
    public Optional<Customer> update(Customer user) {
        Optional<Customer> customer = customerRepository.findById(user.getId());
        if (customer.isPresent()) {
            return Optional.of(customerRepository.save(user));
        } else {
            return Optional.empty();
        }
    }

    // --------------------------------------------------------------------------------------------
    // --------------------------------------- DELETE ---------------------------------------------
    // --------------------------------------------------------------------------------------------
    @Override
    public Optional<Customer> delete(UUID id) {
        Optional<Customer> user = customerRepository.findById(id);
        user.ifPresent(customer -> customerRepository.delete(customer));
        return user;
    }
}
