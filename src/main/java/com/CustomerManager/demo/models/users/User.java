package com.CustomerManager.demo.models.users;

import com.CustomerManager.demo.annotations.ValidFiscalCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@MappedSuperclass
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    @CreationTimestamp
    private Date createDate;

    @UpdateTimestamp
    private Date updateDate;

    @NotNull
    @Size(min = 5, max = 50)
    @Column(unique = true, length = 50)
    private String firstName;

    @NotNull
    @Size(min = 5, max = 50)
    @Column(unique = true, length = 50)
    private String lastName;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ValidFiscalCode
    @Size(min = 16, max = 16)
    @Column(unique = true, length = 16)
    private String cf;

    private String piva;

    private Date lastLoginDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {return true;}

    @Override
    public boolean isAccountNonLocked() {return true;}

    @Override
    public boolean isCredentialsNonExpired() {return true;}

    @Override
    public boolean isEnabled() {return true;}
}
