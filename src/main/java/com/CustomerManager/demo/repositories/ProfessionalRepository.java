package com.CustomerManager.demo.repositories;

import com.CustomerManager.demo.models.users.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, UUID> {
    Optional<Professional> findProfessionalByEmail(String email);
}
