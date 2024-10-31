package com.CustomerManager.demo.services.security;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompositeDetailsService implements UserDetailsService {

    @Autowired
    private ProfessionalDetailsService professionalDetailsService;

    @Autowired
    private CustomerDetailsService customerDetailsService;

    @Autowired
    private AdminDetailsService adminDetailsService;

    private List<CustomUserService> services;

    @PostConstruct
    public void setServices() {
        List<CustomUserService> services = new ArrayList<>();
        services.add(this.professionalDetailsService);
        services.add(this.customerDetailsService);
        services.add(this.adminDetailsService);
        this.services = services;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        for (CustomUserService service : services) {
            try {
                UserDetails user = service.loadUserByUsername(username);
                return user;
            } catch (UsernameNotFoundException e) {
                continue;
            }
        }
        throw new UsernameNotFoundException("User with email: " + username + " Not Found");
    }
}
