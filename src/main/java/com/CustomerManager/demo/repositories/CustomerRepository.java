package com.CustomerManager.demo.repositories;

import com.CustomerManager.demo.models.users.Customer;
import com.CustomerManager.demo.models.users.Professional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findCustomerByEmail(String email);
    Page<Customer> findCustomersByProfessionals(List<Professional> professionals, Pageable pageable);
}
