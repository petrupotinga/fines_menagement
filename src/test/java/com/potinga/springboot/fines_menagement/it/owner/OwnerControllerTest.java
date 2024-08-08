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
        //  Given
        CreateOwnerRequest createOwnerRequest = RandomCreateOwnerRequest.builder().build().get();

        // When
        OwnerCreatedResponse ownerCreatedResponse = ownerApiClient.createOwner(port, createOwnerRequest);

        // Then
        assertThat(ownerCreatedResponse)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id") // Ignores the 'id' field in comparison
                        .build())
                .isEqualTo(OwnerCreatedResponse.builder()
                        .idnp(createOwnerRequest.getIdnp())
                        .firstName(createOwnerRequest.getFirstName())
                        .lastName(createOwnerRequest.getLastName())
                        .birthDate(createOwnerRequest.getBirthDate())
                        .address(createOwnerRequest.getAddress())
                        .phoneNumber(createOwnerRequest.getPhoneNumber())
                        .build());
    }

    @Test
    @DisplayName("Get owner by id")
    void getOwnerById() {
        ownerRepository.deleteAll();
        //  Given
        OwnerEntity ownerEntity = RandomOwner.builder().build().get();
        OwnerEntity persistedOwner = ownerRepository.save(ownerEntity);

        // When
        OwnerByIdResponse ownerByIdResponse = ownerApiClient.getOwnerById(port, persistedOwner.getId());

        // Then
        assertThat(ownerByIdResponse.getId()).isNotNull();

        assertThat(ownerByIdResponse)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id") // Ignores the 'id' field in comparison
                        .build())
                .isEqualTo(OwnerByIdResponse.builder()
                        .idnp(ownerEntity.getIdnp())
                        .firstName(ownerEntity.getFirstName())
                        .lastName(ownerEntity.getLastName())
                        .birthDate(ownerEntity.getBirthDate())
                        .address(ownerEntity.getAddress())
                        .phoneNumber(ownerEntity.getPhoneNumber())
                        .build());
    }

    @Test
    @DisplayName("Get owner by idnp")
    void getOwnerByIdnp() {
        ownerRepository.deleteAll();

        // Given
        OwnerEntity ownerEntity = RandomOwner.builder().build().get();
        OwnerEntity persistedOwner = ownerRepository.save(ownerEntity);

        // When
        OwnerByIdnpResponse ownerByIdnpResponse = ownerApiClient.getOwnerByIdnp(port, persistedOwner.getIdnp());

        // Then
        assertThat(ownerByIdnpResponse.getIdnp()).isNotNull();

        assertThat(ownerByIdnpResponse)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id") // Ignoră câmpul 'id' în comparație
                        .build())
                .isEqualTo(OwnerByIdnpResponse.builder()
                        .idnp(ownerEntity.getIdnp())
                        .firstName(ownerEntity.getFirstName())
                        .lastName(ownerEntity.getLastName())
                        .birthDate(ownerEntity.getBirthDate())
                        .address(ownerEntity.getAddress())
                        .phoneNumber(ownerEntity.getPhoneNumber())
                        .build());
    }

    @Test
    @DisplayName("Get owner by firstName, lastName and birthDate")
    void getByFirstNameLastNameBirthDate() {
        ownerRepository.deleteAll();

        // Given
        OwnerEntity ownerEntity = RandomOwner.builder().build().get();
        OwnerEntity persistedOwner = ownerRepository.save(ownerEntity);

        // When
        OwnerByNameAndDateResponse ownerByNameResponse = ownerApiClient.getByFirstNameLastNameBirthDate(port, persistedOwner.getFirstName(), persistedOwner.getLastName(), persistedOwner.getBirthDate());

        // Then
        assertThat(ownerByNameResponse).isNotNull();
        assertThat(ownerByNameResponse.getFirstName()).isEqualTo(persistedOwner.getFirstName());
        assertThat(ownerByNameResponse.getLastName()).isEqualTo(persistedOwner.getLastName());
        assertThat(ownerByNameResponse.getBirthDate()).isEqualTo(persistedOwner.getBirthDate());

        assertThat(ownerByNameResponse)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id") // Ignoră câmpul 'id' în comparație
                        .build())
                .isEqualTo(OwnerByNameAndDateResponse.builder()
                        .idnp(ownerEntity.getIdnp())
                        .firstName(ownerEntity.getFirstName())
                        .lastName(ownerEntity.getLastName())
                        .birthDate(ownerEntity.getBirthDate())
                        .address(ownerEntity.getAddress())
                        .phoneNumber(ownerEntity.getPhoneNumber())
                        .build());
    }


    @Test
    @DisplayName("Get all owners")
    void getAllOwnersTest() {
        ownerRepository.deleteAll();

        //  Given
        OwnerEntity ownerEntityTransient1 = RandomOwner.builder().build().get();
        OwnerEntity ownerEntityTransient2 = RandomOwner.builder().build().get();

        ownerRepository.saveAll(List.of(ownerEntityTransient1, ownerEntityTransient2));

        // When
        List<AllOwnerResponse> allOwners = ownerApiClient.getAllOwners(port);

        // Then
        allOwners.forEach(owner -> assertThat(owner.getId()).isNotNull());

        assertThat(allOwners)
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")  // Ignores the 'id' field in comparison
                .containsAll(List.of(
                        AllOwnerResponse.builder()
                                .idnp(ownerEntityTransient1.getIdnp())
                                .firstName(ownerEntityTransient1.getFirstName())
                                .lastName(ownerEntityTransient1.getLastName())
                                .address(ownerEntityTransient1.getAddress())
                                .birthDate(ownerEntityTransient1.getBirthDate())
                                .phoneNumber(ownerEntityTransient1.getPhoneNumber())
                                .build(),
                        AllOwnerResponse.builder()
                                .idnp(ownerEntityTransient2.getIdnp())
                                .firstName(ownerEntityTransient2.getFirstName())
                                .lastName(ownerEntityTransient2.getLastName())
                                .birthDate(ownerEntityTransient2.getBirthDate())
                                .address(ownerEntityTransient2.getAddress())
                                .phoneNumber(ownerEntityTransient2.getPhoneNumber())
                                .build()
                ));
    }

    @Test
    @DisplayName("Update an owner")
    void updateOwnerTest() {
        ownerRepository.deleteAll();
        //  Given
        OwnerEntity ownerTransient = RandomOwner.builder().build().get();
        OwnerEntity persistedOwner = ownerRepository.save(ownerTransient);
        Long ownerId = persistedOwner.getId();

        UpdateOwnerRequest updateOwnerRequest = RandomUpdateOwnerRequest.builder().build().get();

        // When
        UpdateOwnerResponse updateOwnerResponse = ownerApiClient.updateOwner(port, ownerId, updateOwnerRequest);

        // Then
        OwnerEntity ownerInDb = ownerRepository.findById(ownerId).orElseThrow();

        assertThat(updateOwnerResponse)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id") // Ignores the 'id' field in comparison
                        .build())
                .isEqualTo(
                        UpdateOwnerResponse.builder()
                                .idnp(updateOwnerRequest.getIdnp())
                                .firstName(updateOwnerRequest.getFirstName())
                                .lastName(updateOwnerRequest.getLastName())
                                .birthdate(updateOwnerRequest.getBirthDate())
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
                                .idnp(ownerInDb.getIdnp())
                                .firstName(ownerInDb.getFirstName())
                                .lastName(ownerInDb.getLastName())
                                .birthdate(ownerInDb.getBirthDate())
                                .address(ownerInDb.getAddress())
                                .phoneNumber(ownerInDb.getPhoneNumber())
                                .build()
                );
    }

    @Test
    @DisplayName("Delete an owner")
    void deleteOwner() {
        // Given
        OwnerEntity ownerTransient = RandomOwner.builder().build().get();
        OwnerEntity persistedOwner = ownerRepository.save(ownerTransient);

        // When
        ownerApiClient.deleteOwner(port, persistedOwner.getId());

        // Then
        boolean vehicleExists = ownerRepository.existsById(persistedOwner.getId());
        assertThat(vehicleExists).isFalse();
    }
}
