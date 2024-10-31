package com.CustomerManager.demo.controllers;

import com.CustomerManager.demo.models.user.User;
import com.CustomerManager.demo.repositories.CustomerRepository;
import com.CustomerManager.demo.repositories.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class Test {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProfessionalRepository professionalRepository;

    @PostMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
