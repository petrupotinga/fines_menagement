package com.potinga.springboot.fines_menagement.controller;

import com.potinga.springboot.fines_menagement.dto.rest.fine.AllFineResponse;
import com.potinga.springboot.fines_menagement.dto.rest.fine.CreateFineRequest;
import com.potinga.springboot.fines_menagement.dto.rest.fine.FineByIdResponse;
import com.potinga.springboot.fines_menagement.dto.rest.fine.FineCreatedResponse;
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
}
