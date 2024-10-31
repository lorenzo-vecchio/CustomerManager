package com.CustomerManager.demo.models.user;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer extends User {

    @ManyToMany
    private List<Professional> professionals;
}
