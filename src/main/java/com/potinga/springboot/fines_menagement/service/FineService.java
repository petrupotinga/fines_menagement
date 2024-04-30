package com.potinga.springboot.fines_menagement.service;

import com.potinga.springboot.fines_menagement.dto.rest.fine.CreateFineReguest;
import com.potinga.springboot.fines_menagement.dto.rest.fine.FineCreatedResponse;
import com.potinga.springboot.fines_menagement.entity.FineEntity;
import com.potinga.springboot.fines_menagement.entity.OwnerEntity;
import com.potinga.springboot.fines_menagement.entity.VehicleEntity;
import com.potinga.springboot.fines_menagement.repository.FineRepository;
import com.potinga.springboot.fines_menagement.repository.OwnerRepository;
import com.potinga.springboot.fines_menagement.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class FineService {
    private final FineRepository fineRepository;
    private final VehicleRepository vehicleRepository;
    private final OwnerRepository ownerRepository;

    @Autowired
    public FineService(FineRepository fineRepository, VehicleRepository vehicleRepository, OwnerRepository ownerRepository) {
        this.fineRepository = fineRepository;
        this.vehicleRepository = vehicleRepository;
        this.ownerRepository = ownerRepository;
    }

    public FineCreatedResponse saveFine(CreateFineReguest request) {

        FineEntity fine = new FineEntity();
        fine.setAmount(request.getAmount());
        fine.setViolation(request.getViolation());
        fine.setDate(request.getDate());
        fine.setLocation(request.getLocation());
        OwnerEntity owner = ownerRepository.findById(request.getOwnerId()).orElseThrow(()
                -> new RuntimeException("Owner not found"));
        fine.setOwner(owner);
        VehicleEntity vehicle = vehicleRepository.findById(request.getVehicleId()).orElseThrow(()
                -> new RuntimeException("Owner not found"));
        fine.setVehicle(vehicle);

        FineEntity saveFine = fineRepository.save(fine);

        FineCreatedResponse response = new FineCreatedResponse();
        response.setFineId(saveFine.getFineid());
        response.setAmount(saveFine.getAmount());
        response.setViolation(saveFine.getViolation());
        response.setDate(saveFine.getDate());
        response.setLocation(saveFine.getLocation());
        response.setOwnerId(saveFine.getOwner().getId());
        response.setVehicleId(saveFine.getVehicle().getId());

        return response;
    }
}