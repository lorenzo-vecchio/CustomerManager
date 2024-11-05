package com.CustomerManager.demo.controllers;

import com.CustomerManager.demo.annotations.IsAdmin;
import com.CustomerManager.demo.repositories.CustomerRepository;
import com.CustomerManager.demo.repositories.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class Test {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProfessionalRepository professionalRepository;

    @GetMapping("/test-connection")
    public String testConnection() {
        return "Hello World";
    }
}
