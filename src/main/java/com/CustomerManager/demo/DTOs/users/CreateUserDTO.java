package com.CustomerManager.demo.DTOs.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserDTO {

    @NotBlank(message = "firstName is mandatory")
    @Size(min = 5, max = 50)
    String firstName;

    @NotBlank(message = "lastName is mandatory")
    @Size(min = 5, max = 50)
    String lastName;

    @NotBlank(message = "email is mandatory")
    @Email(message = "email must be in a valid format")
    String email;

    @NotBlank(message = "password is mandatory")
    // TODO: implement other password rules
    String password;
}
