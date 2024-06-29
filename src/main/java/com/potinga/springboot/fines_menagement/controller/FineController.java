package com.potinga.springboot.fines_menagement.controller;

import com.potinga.springboot.fines_menagement.dto.rest.fine.*;
import com.potinga.springboot.fines_menagement.service.FineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fines")
public class FineController {
    private final FineService fineService;

    public FineController(FineService fineService) {
        this.fineService = fineService;
    }

    @PostMapping
    public FineCreatedResponse createFine(@RequestBody CreateFineRequest request) {
        return fineService.saveFine(request);
    }

    @GetMapping("/{id}")
    public FineByIdResponse getFineById(@PathVariable("id") Long id) {
        return fineService.getFineById(id);
    }

    @GetMapping
    public List<AllFineResponse> getAllFines() {
        return fineService.getAllFines();
    }

    @PutMapping("/{id}")
    public UpdateFineResponse updateFine(@PathVariable Long id, @RequestBody UpdateFineRequest updateRequest) {
        return fineService.updateFine(id, updateRequest);
    }
    @DeleteMapping("/{id}")
    public void deleteFine(@PathVariable Long id) {
        fineService.deleteFine(id);
    }
}
