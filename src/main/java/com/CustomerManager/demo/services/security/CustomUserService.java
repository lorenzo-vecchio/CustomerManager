package com.CustomerManager.demo.services.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserService {
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
