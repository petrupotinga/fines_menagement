package com.potinga.springboot.fines_menagement.controller;

import com.potinga.springboot.fines_menagement.dto.rest.vehicle.CreateVehicleRequest;
import com.potinga.springboot.fines_menagement.dto.rest.vehicle.VehicleCreatedResponse;
import com.potinga.springboot.fines_menagement.service.VehicleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public VehicleCreatedResponse createVehicle(@RequestBody CreateVehicleRequest request) {
        VehicleCreatedResponse response = vehicleService.createVehicle(request);
        return response;
    }
}
