package com.CustomerManager.demo.models.users;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer extends User {

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return List.of(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_CUSTOMER";
            }
        });
    }

    @ManyToMany
    private List<Professional> professionals;
}
