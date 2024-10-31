package com.CustomerManager.demo.services.security;

import com.CustomerManager.demo.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerDetailsService implements CustomUserService {

    @Autowired
    private final CustomerRepository customerRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.customerRepository.findCustomerByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Customer with email: " + username + " not found"));
    }
}
