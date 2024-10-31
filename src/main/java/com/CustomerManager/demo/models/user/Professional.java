package com.CustomerManager.demo.models.user;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Professional extends User {

    @ManyToMany
    private List<Customer> customers;

    @ManyToOne
    private Admin createdBy;
}
