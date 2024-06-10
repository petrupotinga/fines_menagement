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

    @GetMapping
    public List<AllOwnerResponse> getAllOwners() {
        return ownerService.getAllOwners();
    }

    @PutMapping("/{id}")
    public UpdateOwnerResponse updateVehicle(@PathVariable Long id, @RequestBody UpdateOwnerRequest updateRequest) {
        return ownerService.updateVehicle(id, updateRequest);
    }
}
