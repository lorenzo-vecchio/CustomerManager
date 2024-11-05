package com.CustomerManager.demo.models.users;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Professional extends User {

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return List.of(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_PROFESSIONAL";
            }
        });
    }

    @ManyToMany
    private List<Customer> customers;

    @ManyToOne
    private Admin createdBy;
}
