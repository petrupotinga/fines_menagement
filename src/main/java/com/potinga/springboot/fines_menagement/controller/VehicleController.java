package com.potinga.springboot.fines_menagement.controller;

import com.potinga.springboot.fines_menagement.dto.rest.vehicle.AllVehicleResponse;
import com.potinga.springboot.fines_menagement.dto.rest.vehicle.CreateVehicleRequest;
import com.potinga.springboot.fines_menagement.dto.rest.vehicle.VehicleByLPResponse;
import com.potinga.springboot.fines_menagement.dto.rest.vehicle.VehicleCreatedResponse;
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

    //    @GetMapping("/{id}")
//    public VehicleByIdResponse getVehicleById(@PathVariable("id") Long id) {
//        return vehicleService.getVehicleById(id);
//    }
    @GetMapping("/{licensePlate}")
    public VehicleByLPResponse getVehicleByLicensePlate(@PathVariable("licensePlate") String licensePlate) {
        return vehicleService.getVehicleByLicensePlate(licensePlate);
    }

    @GetMapping
    public List<AllVehicleResponse> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }
}
