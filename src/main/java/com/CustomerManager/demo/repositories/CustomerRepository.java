package com.CustomerManager.demo.repositories;

import com.CustomerManager.demo.models.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findCustomerById(UUID id);
    Optional<Customer> findCustomerByEmail(String email);
}