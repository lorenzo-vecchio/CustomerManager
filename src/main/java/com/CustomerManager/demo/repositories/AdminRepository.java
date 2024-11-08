package com.CustomerManager.demo.repositories;

import com.CustomerManager.demo.models.users.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Optional<Admin> findAdminByEmail(String email);
    Page<Admin> findAll(Pageable pageable);
    long count();
}
