package com.potinga.springboot.fines_menagement.controller;

import com.potinga.springboot.fines_menagement.dto.rest.vehicle.*;
import com.potinga.springboot.fines_menagement.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/vin/{vin}")
    public VehicleByVinResponse getVehicleByVin(@PathVariable("vin") String vin) {
        return vehicleService.getVehicleByVin(vin);
    }

    @GetMapping
    public List<AllVehicleResponse> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @PutMapping("/{id}")
    public UpdateVehicleResponse updateVehicle(@PathVariable Long id, @RequestBody UpdateVehicleRequest updateRequest) {
        return vehicleService.updateVehicle(id, updateRequest);
    }

    @PostMapping("/{vehicleId}/transfer")
    public ResponseEntity<Void> transferVehicleToAnotherOwner(
            @PathVariable Long vehicleId,
            @RequestParam Long newOwnerId) {
        vehicleService.transferVehicleToAnotherOwner(vehicleId, newOwnerId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
    }
}
