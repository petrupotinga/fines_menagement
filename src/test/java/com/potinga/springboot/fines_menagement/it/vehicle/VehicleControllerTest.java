package com.potinga.springboot.fines_menagement.it.vehicle;

import com.potinga.springboot.fines_menagement.apiclient.VehicleApiClient;
import com.potinga.springboot.fines_menagement.common.PostgresIntegrationTest;
import com.potinga.springboot.fines_menagement.common.random.owner.RandomOwner;
import com.potinga.springboot.fines_menagement.common.random.vehicle.RandomCreateVehicleRequest;
import com.potinga.springboot.fines_menagement.common.random.vehicle.RandomUpdateVehicleRequest;
import com.potinga.springboot.fines_menagement.common.random.vehicle.RandomVehicle;
import com.potinga.springboot.fines_menagement.dto.rest.vehicle.*;
import com.potinga.springboot.fines_menagement.entity.OwnerEntity;
import com.potinga.springboot.fines_menagement.entity.VehicleEntity;
import com.potinga.springboot.fines_menagement.repository.OwnerRepository;
import com.potinga.springboot.fines_menagement.repository.VehicleRepository;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@PostgresIntegrationTest
class VehicleControllerTest {

    @LocalServerPort
    private String port;

    @Autowired
    private VehicleApiClient vehicleApiClient;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    @BeforeEach
    void setUp() {
        // Curăță baza de date înainte de fiecare test
        ownerRepository.deleteAll();
        vehicleRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        // Curăță baza de date după fiecare test pentru a asigura izolarea testelor
        ownerRepository.deleteAll();
        vehicleRepository.deleteAll();
    }

    @Test
    @DisplayName("Create a new vehicle")
    void createVehicleTest() {
        ownerRepository.deleteAll();
        vehicleRepository.deleteAll();

        //  Given
        OwnerEntity ownerTransient = RandomOwner.builder().build().get();
        OwnerEntity persistedOwner = ownerRepository.save(ownerTransient);

        CreateVehicleRequest createVehicleRequest = RandomCreateVehicleRequest.builder()
                .ownerId(persistedOwner.getId())
                .build().get();

        // When
        VehicleCreatedResponse vehicleCreatedResponse = vehicleApiClient.createVehicle(port, createVehicleRequest);

        // Then
        assertThat(vehicleCreatedResponse)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id") // Ignores the 'id' field in comparison
                        .build())
                .isEqualTo(VehicleCreatedResponse.builder()
                        .vin(createVehicleRequest.getVin())
                        .licensePlate(createVehicleRequest.getLicensePlate())
                        .ownerId(createVehicleRequest.getOwnerId())
                        .make(createVehicleRequest.getMake())
                        .model(createVehicleRequest.getModel())
                        .year(createVehicleRequest.getYear())
                        .build());
    }

    @Test
    @DisplayName("Get all vehicles")
    void getAllVehiclesTest() {
        //  Given
        OwnerEntity ownerTransient = RandomOwner.builder().build().get();
        OwnerEntity persistedOwner = ownerRepository.save(ownerTransient);

        VehicleEntity vehicle1 = RandomVehicle.builder().build().get();
        vehicle1.setOwner(persistedOwner);

        VehicleEntity vehicle2 = RandomVehicle.builder().build().get();
        vehicle2.setOwner(persistedOwner);

        vehicleRepository.saveAll(List.of(vehicle1, vehicle2));

        // When
        List<AllVehicleResponse> allVehicles = vehicleApiClient.getAllVehicles(port);

        // Then
        allVehicles.forEach(vehicle -> assertThat(vehicle.getId()).isNotNull());

        assertThat(allVehicles)
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")  // Ignores the 'id' field in comparison
                .containsAll(Stream.of(
                                AllVehicleResponse.builder()
                                        .vin(vehicle1.getVin())
                                        .licensePlate(vehicle1.getLicensePlate())
                                        .make(vehicle1.getMake())
                                        .model(vehicle1.getModel())
                                        .year(vehicle1.getYear())
                                        .build(),
                                AllVehicleResponse.builder()
                                        .vin(vehicle2.getVin())
                                        .licensePlate(vehicle2.getLicensePlate())
                                        .make(vehicle2.getMake())
                                        .model(vehicle2.getModel())
                                        .year(vehicle2.getYear())
                                        .build()
                        )
                        .peek(vehicle -> vehicle.setOwnerId(persistedOwner.getId()))
                        .toList());
    }

    @Test
    @DisplayName("Get vehicle by licensePlate")
    void getVehicleByLicensePlateTest() {
        // Given
        OwnerEntity ownerTransient = RandomOwner.builder().build().get();
        OwnerEntity persistedOwner = ownerRepository.save(ownerTransient);

        VehicleEntity vehicleTransient = RandomVehicle.builder().build().get();
        vehicleTransient.setOwner(persistedOwner);

        VehicleEntity persistedVehicle = vehicleRepository.save(vehicleTransient);

        // When
        VehicleByLPResponse vehicleResponse = vehicleApiClient.getVehicleByLicensePlate(port, persistedVehicle.getLicensePlate());

        // Then
        assertThat(vehicleResponse.getLicensePlate()).isNotNull();

        assertThat(vehicleResponse)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id") // Ignores the 'id' field in comparison
                        .build())
                .isEqualTo(VehicleByLPResponse.builder()
                        .vin(persistedVehicle.getVin())
                        .licensePlate(persistedVehicle.getLicensePlate())
                        .make(persistedVehicle.getMake())
                        .model(persistedVehicle.getModel())
                        .year(persistedVehicle.getYear())
                        .ownerId(persistedOwner.getId())
                        .build());
    }

    @Test
    @DisplayName("Get vehicle by id")
    void getVehicleByIdTest() {
        //  Given
        OwnerEntity ownerTransient = RandomOwner.builder().build().get();
        OwnerEntity persistedOwner = ownerRepository.save(ownerTransient);

        VehicleEntity vehicleTransient = RandomVehicle.builder().build().get();
        vehicleTransient.setOwner(persistedOwner);

        VehicleEntity persistedVehicle = vehicleRepository.save(vehicleTransient);

        // When
        VehicleByIdResponse vehicleResponse = vehicleApiClient.getVehicleById(port, persistedVehicle.getId());

        // Then
        assertThat(vehicleResponse.getId()).isNotNull();

        assertThat(vehicleResponse)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id") // Ignores the 'id' field in comparison
                        .build())
                .isEqualTo(VehicleByIdResponse.builder()
                        .vin(persistedVehicle.getVin())
                        .licensePlate(persistedVehicle.getLicensePlate())
                        .make(persistedVehicle.getMake())
                        .model(persistedVehicle.getModel())
                        .year(persistedVehicle.getYear())
                        .ownerId(persistedOwner.getId())
                        .build());
    }

    @Test
    @DisplayName("Update vehicle")
    void updateVehicleTest() {
        //  Given
        OwnerEntity ownerTransient = RandomOwner.builder().build().get();
        OwnerEntity persistedOwner = ownerRepository.save(ownerTransient);

        VehicleEntity vehicleTransient = RandomVehicle.builder().build().get();
        vehicleTransient.setOwner(persistedOwner);
        VehicleEntity persistedVehicle = vehicleRepository.save(vehicleTransient);

        UpdateVehicleRequest updateVehicleRequest = RandomUpdateVehicleRequest.builder().build().get();

        Long vehicleId = persistedVehicle.getId();

        // When
        UpdateVehicleResponse updateVehicleResponse = vehicleApiClient.updateVehicle(port, vehicleId, updateVehicleRequest);

        // Then
        VehicleEntity vehicle = vehicleRepository.findById(vehicleId).orElseThrow();

        assertThat(updateVehicleResponse)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id") // Ignores the 'id' field in comparison
                        .build())
                .isEqualTo(
                        UpdateVehicleResponse.builder()
                                .vin(vehicle.getVin())
                                .licensePlate(vehicle.getLicensePlate())
                                .make(vehicle.getMake())
                                .model(vehicle.getModel())
                                .year(vehicle.getYear())
                                .build());

        assertThat(updateVehicleResponse)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id") // Ignores the 'id' field in comparison
                        .build())
                .isEqualTo(
                        UpdateVehicleResponse.builder()
                                .vin(updateVehicleRequest.getVin())
                                .licensePlate(updateVehicleRequest.getLicensePlate())
                                .make(updateVehicleRequest.getMake())
                                .model(updateVehicleRequest.getModel())
                                .year(updateVehicleRequest.getYear())
                                .build());
    }

    @Test
    @DisplayName("Transfer Vehicle to Another Owner")
    void transferVehicleToAnotherOwnerTest() {
        // Given
        OwnerEntity originalOwnerTransient = RandomOwner.builder().build().get();
        OwnerEntity originalPersistedOwner = ownerRepository.save(originalOwnerTransient);

        VehicleEntity vehicleTransient = RandomVehicle.builder().build().get();
        vehicleTransient.setOwner(originalPersistedOwner);
        VehicleEntity persistedVehicle = vehicleRepository.save(vehicleTransient);

        OwnerEntity newOwnerTransient = RandomOwner.builder().build().get();
        OwnerEntity persistedNewOwner = ownerRepository.save(newOwnerTransient);

        // When
        vehicleApiClient.transferVehicleToAnotherOwner(port, persistedVehicle.getId(), persistedNewOwner.getId());

        // Then
        VehicleEntity transferredVehicle = vehicleRepository.findById(persistedVehicle.getId()).orElseThrow();
        assertThat(transferredVehicle.getOwner().getId()).isEqualTo(persistedNewOwner.getId());
    }

    @Test
    @DisplayName("Delete vehicle by id")
    void deleteVehicleTest() {
        //  Given
        OwnerEntity ownerTransient = RandomOwner.builder().build().get();
        OwnerEntity persistedOwner = ownerRepository.save(ownerTransient);

        VehicleEntity vehicleTransient = RandomVehicle.builder().build().get();
        vehicleTransient.setOwner(persistedOwner);
        VehicleEntity persistedVehicle = vehicleRepository.save(vehicleTransient);

        // When
        vehicleApiClient.deleteVehicle(port, persistedVehicle.getId());

        // Then
        boolean vehicleExists = vehicleRepository.existsById(persistedVehicle.getId());
        assertThat(vehicleExists).isFalse();
    }
}
