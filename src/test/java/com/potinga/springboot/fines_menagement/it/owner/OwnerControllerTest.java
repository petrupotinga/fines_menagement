package com.potinga.springboot.fines_menagement.it.owner;

import com.potinga.springboot.fines_menagement.apiclient.OwnerApiClient;
import com.potinga.springboot.fines_menagement.common.PostgresIntegrationTest;
import com.potinga.springboot.fines_menagement.common.random.owner.RandomCreateOwnerRequest;
import com.potinga.springboot.fines_menagement.common.random.owner.RandomOwner;
import com.potinga.springboot.fines_menagement.common.random.owner.RandomUpdateOwnerRequest;
import com.potinga.springboot.fines_menagement.dto.rest.owner.*;
import com.potinga.springboot.fines_menagement.entity.OwnerEntity;
import com.potinga.springboot.fines_menagement.repository.OwnerRepository;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@PostgresIntegrationTest
class OwnerControllerTest {

    @LocalServerPort
    private String port;

    @Autowired
    private OwnerApiClient ownerApiClient;
    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    @DisplayName("Create a new owner")
    void createOwnerTest() {
        ownerRepository.deleteAll();
        //        GIVEN
        CreateOwnerRequest createOwnerRequest = RandomCreateOwnerRequest.builder().build().get();

        //        WHEN
        OwnerCreatedResponse ownerCreatedResponse = ownerApiClient.createOwner(port, createOwnerRequest);

        //        THEN
        OwnerCreatedResponse expectedOwnerCreatedResponse = OwnerCreatedResponse.builder()
                .firstName(createOwnerRequest.getFirstName())
                .lastName(createOwnerRequest.getLastName())
                .address(createOwnerRequest.getAddress())
                .phoneNumber(createOwnerRequest.getPhoneNumber())
                .build();

        assertThat(ownerCreatedResponse)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id") // Ignores the 'id' field in comparison
                        .build())
                .isEqualTo(expectedOwnerCreatedResponse);
    }

    @Test
    @DisplayName("Get owner by id")
    void getOwnerById() {
        ownerRepository.deleteAll();
        //        GIVEN
        OwnerEntity ownerEntity = RandomOwner.builder().build().get();

        OwnerEntity persistedOwner = ownerRepository.save(ownerEntity);

        //        WHEN
        OwnerByIdResponse ownerByIdResponse = ownerApiClient.getOwnerById(port, persistedOwner.getId());

        //        THEN
        OwnerByIdResponse expectedOwnerByIdResponse = OwnerByIdResponse.builder()
                .firstName(ownerEntity.getFirstName())
                .lastName(ownerEntity.getLastName())
                .address(ownerEntity.getAddress())
                .phoneNumber(ownerEntity.getPhoneNumber())
                .build();

        //        THEN
        assertThat(ownerByIdResponse.getId()).isNotNull();

        assertThat(ownerByIdResponse)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id") // Ignores the 'id' field in comparison
                        .build())
                .isEqualTo(expectedOwnerByIdResponse);
    }

    @Test
    @DisplayName("Get all owners")
    void getAllOwnersTest() {
        //        GIVEN
        ownerRepository.deleteAll();

        OwnerEntity ownerEntity1 = RandomOwner.builder().build().get();
        OwnerEntity ownerEntity2 = RandomOwner.builder().build().get();

        ownerRepository.saveAll(List.of(ownerEntity1, ownerEntity2));

        //        WHEN
        List<AllOwnerResponse> allOwners = ownerApiClient.getAllOwners(port);

        //        THEN
        AllOwnerResponse allOwnerResponse1 = AllOwnerResponse.builder()
                .firstName(ownerEntity1.getFirstName())
                .lastName(ownerEntity1.getLastName())
                .address(ownerEntity1.getAddress())
                .phoneNumber(ownerEntity1.getPhoneNumber())
                .build();


        AllOwnerResponse allOwnerResponse2 = AllOwnerResponse.builder()
                .firstName(ownerEntity2.getFirstName())
                .lastName(ownerEntity2.getLastName())
                .address(ownerEntity2.getAddress())
                .phoneNumber(ownerEntity2.getPhoneNumber())
                .build();

        //        THEN
        allOwners.forEach(owner -> assertThat(owner.getId()).isNotNull());

        assertThat(allOwners)
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")  // Ignores the 'id' field in comparison
                .containsAll(List.of(allOwnerResponse1, allOwnerResponse2));
    }

    @Test
    @DisplayName("Update an owner")
    void updateOwnerTest() {
        ownerRepository.deleteAll();
        //        GIVEN
        OwnerEntity ownerTransient = RandomOwner.builder().build().get();

        OwnerEntity persistedOwner = ownerRepository.save(ownerTransient);

        UpdateOwnerRequest updateOwnerRequest = RandomUpdateOwnerRequest.builder().build().get();

        Long ownerId = persistedOwner.getId();

        UpdateOwnerResponse updateOwnerResponse = ownerApiClient.updateOwner(port, ownerId, updateOwnerRequest);

        OwnerEntity ownerInDb = ownerRepository.findById(ownerId).orElseThrow();

        assertThat(updateOwnerResponse)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id") // Ignores the 'id' field in comparison
                        .build())
                .isEqualTo(
                        UpdateOwnerResponse.builder()
                                .firstName(updateOwnerRequest.getFirstName())
                                .lastName(updateOwnerRequest.getLastName())
                                .address(updateOwnerRequest.getAddress())
                                .phoneNumber(updateOwnerRequest.getPhoneNumber())
                                .build()
                );

        assertThat(updateOwnerResponse)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id") // Ignores the 'id' field in comparison
                        .build())
                .isEqualTo(
                        UpdateOwnerResponse.builder()
                                .firstName(ownerInDb.getFirstName())
                                .lastName(ownerInDb.getLastName())
                                .address(ownerInDb.getAddress())
                                .phoneNumber(ownerInDb.getPhoneNumber())
                                .build()
                );
    }

    @Test
    @DisplayName("Delete an owner")
    void deleteOwner() {
        OwnerEntity ownerTransient = RandomOwner.builder().build().get();
        OwnerEntity persistedOwner = ownerRepository.save(ownerTransient);
        ownerApiClient.deleteOwner(port, persistedOwner.getId());
        boolean vehicleExists = ownerRepository.existsById(persistedOwner.getId());
        assertThat(vehicleExists).isFalse();
    }
}
