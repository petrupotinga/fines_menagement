package com.potinga.springboot.fines_menagement.controller;

import com.potinga.springboot.fines_menagement.dto.rest.owner.CreateOwnerRequest;
import com.potinga.springboot.fines_menagement.dto.rest.owner.OwnerByIdResponse;
import com.potinga.springboot.fines_menagement.dto.rest.owner.OwnerCreatedResponse;
import com.potinga.springboot.fines_menagement.service.OwnerService;
import org.springframework.web.bind.annotation.*;

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
}
