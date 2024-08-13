package com.potinga.springboot.fines_menagement.service;

import com.potinga.springboot.fines_menagement.dto.rest.owner.*;
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
        owner.setIdnp(request.getIdnp());
        owner.setFirstName(request.getFirstName());
        owner.setLastName(request.getLastName());
        owner.setBirthDate(request.getBirthDate());
        owner.setAddress(request.getAddress());
        owner.setPhoneNumber(request.getPhoneNumber());

        OwnerEntity saveOwner = ownerRepository.save(owner);

        OwnerCreatedResponse response = new OwnerCreatedResponse();
        response.setId(saveOwner.getId());
        response.setIdnp(request.getIdnp());
        response.setFirstName(saveOwner.getFirstName());
        response.setLastName(saveOwner.getLastName());
        response.setBirthDate(saveOwner.getBirthDate());
        response.setAddress(saveOwner.getAddress());
        response.setPhoneNumber(saveOwner.getPhoneNumber());

        return response;
    }

    public List<AllOwnerResponse> getAllOwners() {
        List<OwnerEntity> owners = ownerRepository.findAll();

        return owners.stream().map(owner -> {
            AllOwnerResponse response = new AllOwnerResponse();
            response.setId(owner.getId());
            response.setIdnp(owner.getIdnp());
            response.setFirstName(owner.getFirstName());
            response.setLastName(owner.getLastName());
            response.setBirthDate(owner.getBirthDate());
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
            response.setIdnp(owner.getIdnp());
            response.setFirstName(owner.getFirstName());
            response.setLastName(owner.getLastName());
            response.setBirthDate(owner.getBirthDate());
            response.setAddress(owner.getAddress());
            response.setPhoneNumber(owner.getPhoneNumber());

            return response;
        } else {
            throw new RuntimeException("Owner not found for id: " + id);
        }
    }
    public OwnerByIdnpResponse getOwnerByIdnp(String idnp) {
        Optional<OwnerEntity> optionalOwner = ownerRepository.findByIdnp(idnp);
        if (optionalOwner.isPresent()) {
            OwnerEntity owner = optionalOwner.get();

            OwnerByIdnpResponse response = new OwnerByIdnpResponse();
            response.setId(owner.getId());
            response.setIdnp(owner.getIdnp());
            response.setFirstName(owner.getFirstName());
            response.setLastName(owner.getLastName());
            response.setBirthDate(owner.getBirthDate());
            response.setAddress(owner.getAddress());
            response.setPhoneNumber(owner.getPhoneNumber());

            return response;
        } else {
            throw new RuntimeException("Owner not found for idnp: " + idnp);
        }
    }

    public OwnerByNameAndDateResponse getByFirstNameLastNameBirthDate(String firstName, String lastName, String birthDate) {
        Optional<OwnerEntity> optionalOwner = ownerRepository.findByFirstNameAndLastNameAndBirthDate(firstName, lastName, birthDate);
        if (optionalOwner.isPresent()) {
            OwnerEntity owner = optionalOwner.get();

            OwnerByNameAndDateResponse response = new OwnerByNameAndDateResponse();
            response.setId(owner.getId());
            response.setFirstName(owner.getFirstName());
            response.setLastName(owner.getLastName());
            response.setBirthDate(owner.getBirthDate());
            response.setIdnp(owner.getIdnp());
            response.setAddress(owner.getAddress());
            response.setPhoneNumber(owner.getPhoneNumber());

            return response;
        } else {
            throw new RuntimeException("Owner not found for FirstName, LastName and Birthdate: " + firstName + " " + lastName + " " + birthDate);
        }
    }

    public UpdateOwnerResponse updateOwner(Long ownerId, UpdateOwnerRequest updateRequest) {
        Optional<OwnerEntity> optionalOwner = ownerRepository.findById(ownerId);
        if (optionalOwner.isPresent()) {
            OwnerEntity owner = optionalOwner.get();
            owner.setIdnp(updateRequest.getIdnp());
            owner.setFirstName(updateRequest.getFirstName());
            owner.setLastName(updateRequest.getLastName());
            owner.setBirthDate(updateRequest.getBirthDate());
            owner.setAddress(updateRequest.getAddress());
            owner.setPhoneNumber(updateRequest.getPhoneNumber());

            ownerRepository.save(owner);

            UpdateOwnerResponse response = new UpdateOwnerResponse();
            response.setIdnp(owner.getIdnp());
            response.setFirstName(owner.getFirstName());
            response.setLastName(owner.getLastName());
            response.setBirthdate(owner.getBirthDate());
            response.setAddress(owner.getAddress());
            response.setPhoneNumber(owner.getPhoneNumber());

            return response;
        } else {
            throw new RuntimeException("Owner with ID " + ownerId + " not found");
        }
    }
    public void deleteOwner(Long ownerId){
        OwnerEntity owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found with id: " + ownerId));

        ownerRepository.delete(owner);
    }
}
