package com.potinga.springboot.fines_menagement.service;

import com.potinga.springboot.fines_menagement.dto.rest.vehicle.*;
import com.potinga.springboot.fines_menagement.entity.OwnerEntity;
import com.potinga.springboot.fines_menagement.entity.VehicleEntity;
import com.potinga.springboot.fines_menagement.repository.OwnerRepository;
import com.potinga.springboot.fines_menagement.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        public VehicleByIdResponse getVehicleById(Long id) {

        Optional<VehicleEntity> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            VehicleEntity vehicle = optionalVehicle.get();
            VehicleByIdResponse response = new VehicleByIdResponse();
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
    public List<AllVehicleResponse> getAllVehicles() {
        List<VehicleEntity> vehicles = vehicleRepository.findAll();
        return vehicles.stream().map(vehicle -> {
            AllVehicleResponse response = new AllVehicleResponse();
            response.setId(vehicle.getId());
            response.setMake(vehicle.getMake());
            response.setModel(vehicle.getModel());
            response.setVin(vehicle.getVin());
            response.setYear(vehicle.getYear());
            response.setLicensePlate(vehicle.getLicensePlate());
            response.setOwnerId(vehicle.getOwner().getId());
            return response;
        }).collect(Collectors.toList());
    }
    public VehicleByLPResponse getVehicleByLicensePlate(String licensePlate) {
        Optional<VehicleEntity> optionalVehicle = vehicleRepository.findByLicensePlate(licensePlate);
        if (optionalVehicle.isPresent()) {
            VehicleEntity vehicle = optionalVehicle.get();
            VehicleByLPResponse response = new VehicleByLPResponse();
            response.setId(vehicle.getId());
            response.setMake(vehicle.getMake());
            response.setModel(vehicle.getModel());
            response.setVin(vehicle.getVin());
            response.setYear(vehicle.getYear());
            response.setLicensePlate(vehicle.getLicensePlate());
            response.setOwnerId(vehicle.getOwner().getId());
            return response;
        } else {
            throw new RuntimeException("Vehicle with license plate " + licensePlate + " not found");
        }
    }
    public VehicleByVinResponse getVehicleByVin(String vin) {
        Optional<VehicleEntity> optionalVehicle = vehicleRepository.findByVin(vin);
        if (optionalVehicle.isPresent()) {
            VehicleEntity vehicle = optionalVehicle.get();
            VehicleByVinResponse response = new VehicleByVinResponse();
            response.setId(vehicle.getId());
            response.setMake(vehicle.getMake());
            response.setModel(vehicle.getModel());
            response.setVin(vehicle.getVin());
            response.setYear(vehicle.getYear());
            response.setLicensePlate(vehicle.getLicensePlate());
            response.setOwnerId(vehicle.getOwner().getId());
            return response;
        } else {
            throw new RuntimeException("Vehicle with vin " + vin + " not found");
        }
    }
    public UpdateVehicleResponse updateVehicle(Long vehicleId, UpdateVehicleRequest updateRequest) {
        Optional<VehicleEntity> optionalVehicle = vehicleRepository.findById(vehicleId);
        if (optionalVehicle.isPresent()) {
            VehicleEntity vehicle = optionalVehicle.get();
            vehicle.setMake(updateRequest.getMake());
            vehicle.setModel(updateRequest.getModel());
            vehicle.setVin(updateRequest.getVin());
            vehicle.setYear(updateRequest.getYear());
            vehicle.setLicensePlate(updateRequest.getLicensePlate());

            vehicleRepository.save(vehicle);

            UpdateVehicleResponse response = new UpdateVehicleResponse();
            response.setMake(vehicle.getMake());
            response.setModel(vehicle.getModel());
            response.setVin(vehicle.getVin());
            response.setYear(vehicle.getYear());
            response.setLicensePlate(vehicle.getLicensePlate());

            return response;
        } else {
            throw new RuntimeException("Vehicle with ID " + vehicleId + " not found");
        }
    }
    public void transferVehicleToAnotherOwner(Long vehicleId, Long newOwnerId) {
        VehicleEntity vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + vehicleId));

        OwnerEntity newOwner = ownerRepository.findById(newOwnerId)
                .orElseThrow(() -> new RuntimeException("Owner not found with id: " + newOwnerId));

        vehicle.setOwner(newOwner);

        vehicleRepository.save(vehicle);
    }
    public void deleteVehicle(Long vehicleId){
        VehicleEntity vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + vehicleId));

        vehicleRepository.delete(vehicle);
    }
}
