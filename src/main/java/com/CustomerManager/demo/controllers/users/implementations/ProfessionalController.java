package com.CustomerManager.demo.controllers.users.implementations;

import com.CustomerManager.demo.DTOs.users.CreateUserDTO;
import com.CustomerManager.demo.annotations.IsAdmin;
import com.CustomerManager.demo.annotations.IsAdminOrProfessional;
import com.CustomerManager.demo.controllers.users.interfaces.UserControllerCrudInterface;
import com.CustomerManager.demo.models.users.Admin;
import com.CustomerManager.demo.models.users.Professional;
import com.CustomerManager.demo.services.users.implementations.ProfessionalService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users/professionals")
@Tag(name = "Professional management")
public class ProfessionalController implements UserControllerCrudInterface<Professional> {

    @Autowired
    private ProfessionalService professionalService;

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
    public ResponseEntity<Professional> create(@Valid @RequestBody CreateUserDTO createUserDTO) {
        Professional professional = professionalService.create(createUserDTO);
        return ResponseEntity.ok(professional);
    }

    // --------------------------------------------------------------------------------------------
    // ----------------------------------------- READ ---------------------------------------------
    // --------------------------------------------------------------------------------------------
    @Override
    @Operation(
            summary = "Read (list)",
            description = "Necessary role: ADMIN | PROFESSIONAL"
    )
    @GetMapping("")
    @IsAdminOrProfessional
    public ResponseEntity<Page<Professional>> read(
            @SortDefault(sort = "id")
            @PageableDefault(size = 15)
            @ParameterObject final Pageable pageable
    ) {
        return ResponseEntity.ok(professionalService.read(pageable));
    }

    @Override
    @Operation(
            summary = "Read (single)",
            description = "Necessary role: ADMIN | PROFESSIONAL"
    )
    @GetMapping("/{id}")
    @IsAdminOrProfessional
    public ResponseEntity<Professional> read(@Valid @PathVariable UUID id) {
        return ResponseEntity.of(professionalService.read(id));
    }

    // --------------------------------------------------------------------------------------------
    // --------------------------------------- UPDATE ---------------------------------------------
    // --------------------------------------------------------------------------------------------
    @Override
    @Operation(
            summary = "Update (single)",
            description = "Necessary role: ADMIN | PROFESSIONAL (only if updated user is same as logged user)"
    )
    @PatchMapping("")
    @IsAdminOrProfessional
    @PreAuthorize("!hasRole('ROLE_PROFESSIONAL') or (#user.id == authentication.principal.id)") // if user is professional authorize only if manipulating self // TODO: test this
    public ResponseEntity<Professional> update(@RequestBody Professional user) {
        return ResponseEntity.of(professionalService.update(user));
    }

    // --------------------------------------------------------------------------------------------
    // --------------------------------------- DELETE ---------------------------------------------
    // --------------------------------------------------------------------------------------------
    @Override
    @Operation(
            summary = "Delete (single)",
            description = "Necessary role: ADMIN | PROFESSIONAL (only if updated user is same as logged user)"
    )
    @DeleteMapping("/{id}")
    @IsAdminOrProfessional
    @PreAuthorize("!hasRole('ROLE_PROFESSIONAL') or (#id == authentication.principal.id)") // if user is professional authorize only if manipulating self // TODO: test this
    public ResponseEntity<Professional> delete(@Valid @PathVariable UUID id) {
        return ResponseEntity.of(professionalService.delete(id));
    }
}
