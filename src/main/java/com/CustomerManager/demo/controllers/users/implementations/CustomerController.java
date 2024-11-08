package com.CustomerManager.demo.controllers.users.implementations;

import com.CustomerManager.demo.DTOs.users.CreateUserDTO;
import com.CustomerManager.demo.annotations.IsAdminOrProfessional;
import com.CustomerManager.demo.annotations.IsProfessional;
import com.CustomerManager.demo.controllers.users.interfaces.UserControllerCrudInterface;
import com.CustomerManager.demo.models.users.Customer;
import com.CustomerManager.demo.services.users.implementations.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users/customers")
@Tag(name = "Customer Management")
public class CustomerController implements UserControllerCrudInterface<Customer> {

    @Autowired
    private CustomerService customerService;

    // --------------------------------------------------------------------------------------------
    // --------------------------------------- CREATE ---------------------------------------------
    // --------------------------------------------------------------------------------------------
    @Override
    @Operation(
            summary = "Create (single)",
            description = "Necessary role: PROFESSIONAL"
    )
    @PostMapping("")
    @IsProfessional
    public ResponseEntity<Customer> create(CreateUserDTO createUserDTO) {
        Customer customer = customerService.create(createUserDTO);
        return ResponseEntity.ok(customer);
    }

    // --------------------------------------------------------------------------------------------
    // ----------------------------------------- READ ---------------------------------------------
    // --------------------------------------------------------------------------------------------
    @Override
    @Operation(
            summary = "Read (list)",
            description = "Necessary role: PROFESSIONAL"
    )
    @GetMapping("")
    @IsProfessional
    public ResponseEntity<Page<Customer>> read(
            @SortDefault(sort = "lastName") // TODO: ask if better to change sort default
            @PageableDefault(size = 15)
            @ParameterObject final Pageable pageable
    ) {
        return ResponseEntity.ok(customerService.read(pageable));
    }

    @Operation(
            summary = "Read (single)",
            description = "Necessary role: PROFESSIONAL"
    )
    @GetMapping("/{id}")
    @IsProfessional
    @Override
    @PostAuthorize("returnObject.body.professionals.contains(authentication.principal)") // professional truly is assigned to that customer
    public ResponseEntity<Customer> read(UUID id) {
        return ResponseEntity.of(customerService.read(id));
    }

    // --------------------------------------------------------------------------------------------
    // --------------------------------------- UPDATE ---------------------------------------------
    // --------------------------------------------------------------------------------------------
    @Override
    @Operation(
            summary = "Update (single)",
            description = "Necessary role: CUSTOMER (only if updated user is same as logged user) | PROFESSIONAL (only if it's one of the user's professionals)"
    )
    @PatchMapping("")
    @PreAuthorize("user.professionals.contains(authentication.principal) or user.id == authentication.principal.id")
    public ResponseEntity<Customer> update(Customer user) {
        return ResponseEntity.of(customerService.update(user));
    }

    // --------------------------------------------------------------------------------------------
    // --------------------------------------- DELETE ---------------------------------------------
    // --------------------------------------------------------------------------------------------
    @Override
    @Operation(
            summary = "Delete (single)",
            description = "Necessary role: PROFESSIONAL (only if it's one of the user's professionals)"
    )
    @DeleteMapping("/{id}")
    @IsProfessional
    @PostAuthorize("returnObject.body.professionals.contains(authentication.principal)")
    public ResponseEntity<Customer> delete(UUID id) {
        return null;
    }
}
