package com.CustomerManager.demo.controllers.users.implementations;

import com.CustomerManager.demo.DTOs.users.CreateUserDTO;
import com.CustomerManager.demo.annotations.IsAdmin;
import com.CustomerManager.demo.annotations.IsAdminOrProfessional;
import com.CustomerManager.demo.controllers.users.interfaces.UserControllerCrudInterface;
import com.CustomerManager.demo.models.users.Professional;
import com.CustomerManager.demo.services.users.implementations.ProfessionalService;
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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users/professionals")
@Tag(name = "Professional management")
public class ProfessionalController implements UserControllerCrudInterface<Professional> {

    @Autowired
    ProfessionalService professionalService;

    // --------------------------------------------------------------------------------------------
    // --------------------------------------- CREATE ---------------------------------------------
    // --------------------------------------------------------------------------------------------
    @Override
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
    @GetMapping("/{id}")
    @IsAdminOrProfessional
    public ResponseEntity<Professional> read(@Valid @PathVariable UUID id) {
        return ResponseEntity.of(professionalService.read(id));
    }

    // --------------------------------------------------------------------------------------------
    // --------------------------------------- UPDATE ---------------------------------------------
    // --------------------------------------------------------------------------------------------
    @Override
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
    @DeleteMapping("/{id}")
    @IsAdminOrProfessional
    @PreAuthorize("!hasRole('ROLE_PROFESSIONAL') or (#id == authentication.principal.id)") // if user is professional authorize only if manipulating self // TODO: test this
    public ResponseEntity<Professional> delete(@Valid @PathVariable UUID id) {
        return ResponseEntity.of(professionalService.delete(id));
    }
}
