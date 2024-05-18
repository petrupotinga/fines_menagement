package com.potinga.springboot.fines_menagement.controller;

import com.potinga.springboot.fines_menagement.dto.rest.vehicle.*;
import com.potinga.springboot.fines_menagement.service.VehicleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public VehicleCreatedResponse createVehicle(@RequestBody CreateVehicleRequest request) {
        return vehicleService.createVehicle(request);
    }

    @GetMapping("/id/{id}")
    public VehicleByIdResponse getVehicleById(@PathVariable("id") Long id) {
        return vehicleService.getVehicleById(id);
    }

    @GetMapping("/{licensePlate}")
    public VehicleByLPResponse getVehicleByLicensePlate(@PathVariable("licensePlate") String licensePlate) {
        return vehicleService.getVehicleByLicensePlate(licensePlate);
    }

    @GetMapping
    public List<AllVehicleResponse> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }
}
