package com.CustomerManager.demo.controllers.users.implementations;

import com.CustomerManager.demo.DTOs.users.CreateUserDTO;
import com.CustomerManager.demo.annotations.IsAdmin;
import com.CustomerManager.demo.controllers.users.interfaces.UserControllerCrudInterface;
import com.CustomerManager.demo.models.users.Admin;
import com.CustomerManager.demo.services.users.implementations.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users/admins")
@Tag(name = "Admin management")
public class AdminController implements UserControllerCrudInterface<Admin> {

    @Autowired
    private AdminService adminService;

    // --------------------------------------------------------------------------------------------
    // --------------------------------------- CREATE ---------------------------------------------
    // --------------------------------------------------------------------------------------------
    @Override
    @Operation(
            summary = "Create (single)",
            description = "Necessary role: ADMIN"
    )
    @PostMapping("")
    @IsAdmin
    public ResponseEntity<Admin> create(@Valid @RequestBody CreateUserDTO createUserDTO) {
        Admin admin = adminService.create(createUserDTO);
        return ResponseEntity.ok(admin);
    }

    // --------------------------------------------------------------------------------------------
    // ----------------------------------------- READ ---------------------------------------------
    // --------------------------------------------------------------------------------------------
    @Override
    @Operation(
            summary = "Read (list)",
            description = "Necessary role: ADMIN"
    )
    @GetMapping("")
    @IsAdmin
    public ResponseEntity<Page<Admin>> read(
            @SortDefault(sort = "id")
            @PageableDefault(size = 15)
            @ParameterObject final Pageable pageable
    ) {
        return ResponseEntity.ok(adminService.read(pageable));
    }

    @Override
    @Operation(
            summary = "Read (single)",
            description = "Necessary role: ADMIN"
    )
    @GetMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Admin> read(@Valid @PathVariable UUID id) {
        return ResponseEntity.of(adminService.read(id));
    }

    // --------------------------------------------------------------------------------------------
    // --------------------------------------- UPDATE ---------------------------------------------
    // --------------------------------------------------------------------------------------------
    @Override
    @Operation(
            summary = "Update (single)",
            description = "Necessary role: ADMIN"
    )
    @PatchMapping("")
    @IsAdmin
    public ResponseEntity<Admin> update(@RequestBody Admin user) {
        return ResponseEntity.of(adminService.update(user));
    }

    // --------------------------------------------------------------------------------------------
    // --------------------------------------- DELETE ---------------------------------------------
    // --------------------------------------------------------------------------------------------
    @Override
    @Operation(
            summary = "Delete (single)",
            description = "Necessary role: ADMIN"
    )
    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Admin> delete(@Valid @PathVariable UUID id) {
        return ResponseEntity.of(adminService.delete(id));
    }

}
