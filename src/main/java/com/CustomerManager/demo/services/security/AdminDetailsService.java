package com.CustomerManager.demo.services.security;

import com.CustomerManager.demo.repositories.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDetailsService implements CustomUserService{

    @Autowired
    private final AdminRepository adminRepository;

    public UserDetails loadUserByUsername(String username) {
        return this.adminRepository.findAdminByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Admin with email: " + username + " not found"));
    }
}
