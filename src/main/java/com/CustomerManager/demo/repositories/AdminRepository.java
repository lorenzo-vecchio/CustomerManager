package com.CustomerManager.demo.repositories;

import com.CustomerManager.demo.models.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Optional<Admin> findAdminById(UUID id);
    Optional<Admin> findAdminByEmail(String email);
}
