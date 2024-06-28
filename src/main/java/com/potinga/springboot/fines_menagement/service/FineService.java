package com.potinga.springboot.fines_menagement.service;

import com.potinga.springboot.fines_menagement.dto.rest.fine.*;
import com.potinga.springboot.fines_menagement.entity.FineEntity;
import com.potinga.springboot.fines_menagement.entity.OwnerEntity;
import com.potinga.springboot.fines_menagement.entity.VehicleEntity;
import com.potinga.springboot.fines_menagement.repository.FineRepository;
import com.potinga.springboot.fines_menagement.repository.OwnerRepository;
import com.potinga.springboot.fines_menagement.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FineService {

    private final FineRepository fineRepository;
    private final VehicleRepository vehicleRepository;
    private final OwnerRepository ownerRepository;

    public FineService(FineRepository fineRepository, VehicleRepository vehicleRepository, OwnerRepository ownerRepository) {
        this.fineRepository = fineRepository;
        this.vehicleRepository = vehicleRepository;
        this.ownerRepository = ownerRepository;
    }

    public FineCreatedResponse saveFine(CreateFineRequest request) {
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
        response.setId(saveFine.getId());
        response.setAmount(saveFine.getAmount());
        response.setViolation(saveFine.getViolation());
        response.setDate(saveFine.getDate());
        response.setLocation(saveFine.getLocation());
        response.setOwnerId(saveFine.getOwner().getId());
        response.setVehicleId(saveFine.getVehicle().getId());

        return response;
    }

    public FineByIdResponse getFineById(Long id) {
        Optional<FineEntity> optionalFine = fineRepository.findById(id);
        if (optionalFine.isPresent()) {
            FineEntity fine = optionalFine.get();
            FineByIdResponse response = new FineByIdResponse();
            response.setId(fine.getId());
            response.setAmount(fine.getAmount());
            response.setViolation(fine.getViolation());
            response.setDate(fine.getDate());
            response.setLocation(fine.getLocation());
            response.setOwnerId(fine.getOwner().getId());
            response.setVehicleId(fine.getVehicle().getId());
            return response;
        } else {
            throw new RuntimeException("Fine not found for id: " + id);
        }
    }
    public List<AllFineResponse> getAllFines() {
        List<FineEntity> fines = fineRepository.findAll();

        return fines.stream().map(fine -> {
            AllFineResponse response = new AllFineResponse();
            response.setId(fine.getId());
            response.setAmount(fine.getAmount());
            response.setViolation(fine.getViolation());
            response.setDate(fine.getDate());
            response.setLocation(fine.getLocation());
            response.setOwnerId(fine.getOwner().getId());
            response.setVehicleId(fine.getVehicle().getId());
            return response;
        }).collect(Collectors.toList());
    }
    public UpdateFineResponse updateFine(Long fineId, UpdateFineRequest updateRequest) {
        Optional<FineEntity> optionalFine = fineRepository.findById(fineId);
        if (optionalFine.isPresent()) {
            FineEntity fine = optionalFine.get();
            fine.setAmount(updateRequest.getAmount());
            fine.setViolation(updateRequest.getViolation());
            fine.setDate(updateRequest.getDate());
            fine.setLocation(updateRequest.getLocation());

            fineRepository.save(fine);

            UpdateFineResponse response = new UpdateFineResponse();
            response.setAmount(fine.getAmount());
            response.setViolation(fine.getViolation());
            response.setDate(fine.getDate());
            response.setLocation(fine.getLocation());

            return response;
        } else {
            throw new RuntimeException("Fine with ID " + fineId + " not found");
        }
    }
    public void deleteFine(Long fineId){
        FineEntity fine = fineRepository.findById(fineId)
                .orElseThrow(() -> new RuntimeException("Fine not found with id: " + fineId));

        fineRepository.delete(fine);
    }
}