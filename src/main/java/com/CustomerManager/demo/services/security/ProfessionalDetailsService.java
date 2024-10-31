package com.CustomerManager.demo.services.security;

import com.CustomerManager.demo.repositories.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessionalDetailsService implements CustomUserService {

    @Autowired
    private final ProfessionalRepository professionalRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.professionalRepository.findProfessionalByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Professional with email: " + username + " not found"));
    }
}
