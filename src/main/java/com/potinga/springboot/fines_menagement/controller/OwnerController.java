package com.potinga.springboot.fines_menagement.controller;

import com.potinga.springboot.fines_menagement.dto.rest.owner.*;
import com.potinga.springboot.fines_menagement.service.OwnerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/owners")
public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping
    public OwnerCreatedResponse createOwner(@RequestBody CreateOwnerRequest request) {
        return ownerService.createOwner(request);
    }

    @GetMapping("/{id}")
    public OwnerByIdResponse getOwnerById(@PathVariable("id") Long id) {
        return ownerService.getOwnerById(id);
    }

    @GetMapping("/idnp/{idnp}")
    public OwnerByIdnpResponse getOwnerByIdnp(@PathVariable("idnp") String idnp) {
        return ownerService.getOwnerByIdnp(idnp);
    }

    @GetMapping("/{firstName}/{lastName}/{birthDate}")
    public OwnerByNameAndDateResponse getByFirstNameLastNameBirthDate(
            @PathVariable("firstName") String firstName,
            @PathVariable("lastName") String lastName,
            @PathVariable("birthDate") String birthDate) {
        return ownerService.getByFirstNameLastNameBirthDate(firstName, lastName, birthDate);
    }

    @GetMapping
    public List<AllOwnerResponse> getAllOwners() {
        return ownerService.getAllOwners();
    }

    @PutMapping("/{id}")
    public UpdateOwnerResponse updateOwner(@PathVariable Long id, @RequestBody UpdateOwnerRequest updateRequest) {
        return ownerService.updateOwner(id, updateRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwner(id);
    }
}
