package com.potinga.springboot.fines_menagement.service;

import com.potinga.springboot.fines_menagement.dto.rest.owner.AllOwnerResponse;
import com.potinga.springboot.fines_menagement.dto.rest.owner.CreateOwnerRequest;
import com.potinga.springboot.fines_menagement.dto.rest.owner.OwnerCreatedResponse;
import com.potinga.springboot.fines_menagement.dto.rest.owner.OwnerByIdResponse;
import com.potinga.springboot.fines_menagement.entity.OwnerEntity;
import com.potinga.springboot.fines_menagement.repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }
    public OwnerCreatedResponse createOwner(CreateOwnerRequest request) {
        OwnerEntity owner = new OwnerEntity();

        owner.setFirstName(request.getFirstName());
        owner.setLastName(request.getLastName());
        owner.setAddress(request.getAddress());
        owner.setPhoneNumber(request.getPhoneNumber());

        OwnerEntity saveOwner = ownerRepository.save(owner);

        OwnerCreatedResponse response = new OwnerCreatedResponse();
        response.setId(saveOwner.getId());
        response.setFirstName(saveOwner.getFirstName());
        response.setLastName(saveOwner.getLastName());
        response.setAddress(saveOwner.getAddress());
        response.setPhoneNumber(saveOwner.getPhoneNumber());

        return response;
    }

    public List<AllOwnerResponse> getAllOwners() {
        List<OwnerEntity> owners = ownerRepository.findAll();

        return owners.stream().map(owner -> {
            AllOwnerResponse response = new AllOwnerResponse();
            response.setId(owner.getId());
            response.setFirstName(owner.getFirstName());
            response.setLastName(owner.getLastName());
            response.setAddress(owner.getAddress());
            response.setPhoneNumber(owner.getPhoneNumber());
            return response;
        }).collect(Collectors.toList());
    }
    public OwnerByIdResponse getOwnerById(Long id) {
        Optional<OwnerEntity> optionalOwner = ownerRepository.findById(id);
        if (optionalOwner.isPresent()) {
            OwnerEntity owner = optionalOwner.get();

            OwnerByIdResponse response = new OwnerByIdResponse();
            response.setId(owner.getId());
            response.setFirstName(owner.getFirstName());
            response.setLastName(owner.getLastName());
            response.setAddress(owner.getAddress());
            response.setPhoneNumber(owner.getPhoneNumber());

            return response;
        } else {
            throw new RuntimeException("Owner not found for id: " + id);
        }
    }
}
