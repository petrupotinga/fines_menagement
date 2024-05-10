package com.potinga.springboot.fines_menagement.service;

import com.potinga.springboot.fines_menagement.dto.rest.vehicle.CreateVehicleRequest;
import com.potinga.springboot.fines_menagement.dto.rest.vehicle.VehicleCreatedResponse;
import com.potinga.springboot.fines_menagement.entity.OwnerEntity;
import com.potinga.springboot.fines_menagement.entity.VehicleEntity;
import com.potinga.springboot.fines_menagement.repository.OwnerRepository;
import com.potinga.springboot.fines_menagement.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final OwnerRepository ownerRepository;

    public VehicleService(VehicleRepository vehicleRepository, OwnerRepository ownerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.ownerRepository = ownerRepository;
    }

    public VehicleCreatedResponse createVehicle(CreateVehicleRequest request) {
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setMake(request.getMake());
        vehicle.setModel(request.getModel());
        vehicle.setVin(request.getVin());
        vehicle.setYear(request.getYear());
        vehicle.setLicensePlate(request.getLicensePlate());
        OwnerEntity owner = ownerRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        vehicle.setOwner(owner);

        VehicleEntity saveVehicle = vehicleRepository.save(vehicle);

        VehicleCreatedResponse response = new VehicleCreatedResponse();
        response.setId(saveVehicle.getId());
        response.setMake(saveVehicle.getMake());
        response.setModel(saveVehicle.getModel());
        response.setVin(saveVehicle.getVin());
        response.setYear(saveVehicle.getYear());
        response.setLicensePlate(saveVehicle.getLicensePlate());
        response.setOwnerId(saveVehicle.getOwner().getId());

        return response;
    }

    public VehicleCreatedResponse getVehicleById(Long id) {
        Optional<VehicleEntity> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            VehicleEntity vehicle = optionalVehicle.get();
            VehicleCreatedResponse response = new VehicleCreatedResponse();
            response.setId(vehicle.getId());
            response.setMake(vehicle.getMake());
            response.setModel(vehicle.getModel());
            response.setVin(vehicle.getVin());
            response.setYear(vehicle.getYear());
            response.setLicensePlate(vehicle.getLicensePlate());
            response.setOwnerId(vehicle.getOwner().getId());
            return response;
        } else {
            throw new RuntimeException("Vehicle not found for id: " + id);
        }
    }
}
