package com.potinga.springboot.fines_menagement.it.owner;

import com.potinga.springboot.fines_menagement.apiclient.OwnerApiClient;
import com.potinga.springboot.fines_menagement.common.PostgresIntegrationTest;
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
        CreateOwnerRequest createOwnerRequest = new CreateOwnerRequest();
        createOwnerRequest.setFirstName("Vasea");
        createOwnerRequest.setLastName("Dodon");
        createOwnerRequest.setAddress("Alba Iulia 55");
        createOwnerRequest.setPhoneNumber("060232456");

        //        WHEN
        OwnerCreatedResponse ownerCreatedResponse = ownerApiClient.createOwner(port, createOwnerRequest);

        //        THEN
        OwnerCreatedResponse expectedOwnerCreatedResponse = new OwnerCreatedResponse();
        expectedOwnerCreatedResponse.setFirstName("Vasea");
        expectedOwnerCreatedResponse.setLastName("Dodon");
        expectedOwnerCreatedResponse.setAddress("Alba Iulia 55");
        expectedOwnerCreatedResponse.setPhoneNumber("060232456");

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
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setFirstName("Vasea");
        ownerEntity.setLastName("Dodon");
        ownerEntity.setAddress("Alba Iulia 55");
        ownerEntity.setPhoneNumber("060232456");

        OwnerEntity persistedOwner = ownerRepository.save(ownerEntity);

        //        WHEN
        OwnerByIdResponse ownerByIdResponse = ownerApiClient.getOwnerById(port, persistedOwner.getId());

        //        THEN
        OwnerByIdResponse expectedOwnerByIdResponse = new OwnerByIdResponse();
        expectedOwnerByIdResponse.setFirstName("Vasea");
        expectedOwnerByIdResponse.setLastName("Dodon");
        expectedOwnerByIdResponse.setAddress("Alba Iulia 55");
        expectedOwnerByIdResponse.setPhoneNumber("060232456");

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

        OwnerEntity ownerEntity1 = new OwnerEntity();
        ownerEntity1.setFirstName("Vasea");
        ownerEntity1.setLastName("Dodon");
        ownerEntity1.setAddress("Alba Iulia 55");
        ownerEntity1.setPhoneNumber("060232456");

        OwnerEntity ownerEntity2 = new OwnerEntity();
        ownerEntity2.setFirstName("Igor");
        ownerEntity2.setLastName("Cataraga");
        ownerEntity2.setAddress("Tudor Vladimirescu 12");
        ownerEntity2.setPhoneNumber("060435262");

        ownerRepository.saveAll(List.of(ownerEntity1, ownerEntity2));

        //        WHEN
        List<AllOwnerResponse> allOwners = ownerApiClient.getAllOwners(port);

        //        THEN
        AllOwnerResponse allOwnerResponse1 = new AllOwnerResponse();
        allOwnerResponse1.setFirstName("Vasea");
        allOwnerResponse1.setLastName("Dodon");
        allOwnerResponse1.setAddress("Alba Iulia 55");
        allOwnerResponse1.setPhoneNumber("060232456");

        AllOwnerResponse allOwnerResponse2 = new AllOwnerResponse();
        allOwnerResponse2.setFirstName("Igor");
        allOwnerResponse2.setLastName("Cataraga");
        allOwnerResponse2.setAddress("Tudor Vladimirescu 12");
        allOwnerResponse2.setPhoneNumber("060435262");

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
        OwnerEntity ownerTransient = new OwnerEntity();
        ownerTransient.setFirstName("Vasea");
        ownerTransient.setLastName("Dodon");
        ownerTransient.setAddress("Alba Iulia 55");
        ownerTransient.setPhoneNumber("060232456");

        OwnerEntity persistedOwner = ownerRepository.save(ownerTransient);

        UpdateOwnerRequest updateOwnerRequest = new UpdateOwnerRequest();
        updateOwnerRequest.setFirstName("Ion");
        updateOwnerRequest.setLastName("Creanga");
        updateOwnerRequest.setAddress("Str. Eminescu 10");
        updateOwnerRequest.setPhoneNumber("070123456");

        Long ownerId = persistedOwner.getId();

        ownerApiClient.updateOwner(port, ownerId, updateOwnerRequest);

        OwnerByIdResponse updatedVehicle = ownerApiClient.getOwnerById(port, ownerId);

        assertThat(updatedVehicle.getFirstName()).isEqualTo("Ion");
        assertThat(updatedVehicle.getLastName()).isEqualTo("Creanga");
        assertThat(updatedVehicle.getAddress()).isEqualTo("Str. Eminescu 10");
        assertThat(updatedVehicle.getPhoneNumber()).isEqualTo("070123456");

    }

    //    @formatter:off
    private static final TypeReference<CreateOwnerRequest> CREATE_OWNER_REQUEST_TYPE_REFERENCE = new TypeReference<>() {
    };
    private static final TypeReference<OwnerCreatedResponse> OWNER_CREATED_RESPONSE_TYPE_REFERENCE = new TypeReference<>() {
    };
}
