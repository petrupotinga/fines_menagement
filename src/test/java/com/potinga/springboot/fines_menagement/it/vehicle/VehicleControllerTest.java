package com.potinga.springboot.fines_menagement.it.vehicle;

import com.fasterxml.jackson.core.type.TypeReference;
import com.potinga.springboot.fines_menagement.apiclient.VehicleApiClient;
import com.potinga.springboot.fines_menagement.common.PostgresIntegrationTest;
import com.potinga.springboot.fines_menagement.dto.rest.vehicle.*;
import com.potinga.springboot.fines_menagement.entity.OwnerEntity;
import com.potinga.springboot.fines_menagement.entity.VehicleEntity;
import com.potinga.springboot.fines_menagement.repository.OwnerRepository;
import com.potinga.springboot.fines_menagement.repository.VehicleRepository;
import com.potinga.springboot.fines_menagement.utils.JsonReader;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

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

        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setFirstName(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setLastName(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setAddress(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setPhoneNumber(RandomStringUtils.randomNumeric(10));
        OwnerEntity savedOwner = ownerRepository.save(ownerEntity);

        //        GIVEN
        CreateVehicleRequest createVehicleRequest = JsonReader.read("db/mocks/vehicles/createVehicleRequest.json", CREATE_VEHICLE_REQUEST_TYPE_REFERENCE);
        createVehicleRequest.setOwnerId(savedOwner.getId());

        //        WHEN
        VehicleCreatedResponse vehicleCreatedResponse = vehicleApiClient.createVehicle(port, createVehicleRequest);

        //        THEN
        VehicleCreatedResponse expectedVehicleCreatedResponse = JsonReader.read("db/mocks/vehicles/createVehicleResponse.json", VEHICLE_CREATED_RESPONSE_TYPE_REFERENCE);
        expectedVehicleCreatedResponse.setOwnerId(savedOwner.getId());

        assertThat(vehicleCreatedResponse)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id") // Ignores the 'id' field in comparison
                        .build())
                .isEqualTo(expectedVehicleCreatedResponse);
    }

    @Test
    @DisplayName("Get all vehicles")
    void getAllVehiclesTest() {
        //        GIVEN
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setFirstName(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setLastName(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setAddress(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setPhoneNumber(RandomStringUtils.randomNumeric(10));
        OwnerEntity savedOwner = ownerRepository.save(ownerEntity);

        VehicleEntity vehicle1 = new VehicleEntity();
        vehicle1.setMake("Dacia");
        vehicle1.setModel("Logan");
        vehicle1.setVin("XMCLNDABXHY329876");
        vehicle1.setYear(2016);
        vehicle1.setLicensePlate("DCC220");
        vehicle1.setOwner(savedOwner);

        VehicleEntity vehicle2 = new VehicleEntity();
        vehicle2.setMake("Mercedes");
        vehicle2.setModel("E220");
        vehicle2.setVin("PSALNDABXHY329712");
        vehicle2.setYear(2018);
        vehicle2.setLicensePlate("ACD321");
        vehicle2.setOwner(savedOwner);

        vehicleRepository.saveAll(List.of(vehicle1, vehicle2));

        //        GIVEN
        List<AllVehicleResponse> allVehicleResponses = JsonReader.read("db/mocks/vehicles/allVehicles.json", ALL_VEHICLES_TYPE_REFERENCE)
                .stream()
                .peek(vehicle -> vehicle.setOwnerId(savedOwner.getId()))
                .toList();

        //        WHEN
        List<AllVehicleResponse> allVehicles = vehicleApiClient.getAllVehicles(port);

        //        THEN
        allVehicles.forEach(vehicle -> assertThat(vehicle.getId()).isNotNull());

        assertThat(allVehicles)
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")  // Ignores the 'id' field in comparison
                .containsAll(allVehicleResponses);
    }

    @Test
    @DisplayName("Get vehicle by licensePlate")
    void getVehicleByLicensePlateTest() {
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setFirstName(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setLastName(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setAddress(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setPhoneNumber(RandomStringUtils.randomNumeric(10));
        OwnerEntity savedOwner = ownerRepository.save(ownerEntity);

        VehicleEntity vehicleTransient = new VehicleEntity();
        vehicleTransient.setMake("Dacia");
        vehicleTransient.setModel("Logan");
        vehicleTransient.setVin("XMCLNDABXHY329876");
        vehicleTransient.setYear(2016);
        vehicleTransient.setLicensePlate("DCC220");
        vehicleTransient.setOwner(savedOwner);

        VehicleEntity persistedVehicle = vehicleRepository.save(vehicleTransient);

        //        GIVEN
        VehicleByLPResponse expectedVehicle = JsonReader.read("db/mocks/vehicles/vehicleByLicensePlate.json", VEHICLE_BY_LICENSEPLATE_TYPE_REFERENCE);
        expectedVehicle.setOwnerId(savedOwner.getId());

        //        WHEN
        VehicleByLPResponse vehicleResponse = vehicleApiClient.getVehicleByLicensePlate(port, persistedVehicle.getLicensePlate());

        //        THEN
        assertThat(vehicleResponse.getLicensePlate()).isNotNull();

        assertThat(vehicleResponse)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id") // Ignores the 'id' field in comparison
                        .build())
                .isEqualTo(expectedVehicle);
    }

    @Test
    @DisplayName("Get vehicle by id")
    void getVehicleByIdTest() {
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setFirstName(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setLastName(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setAddress(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setPhoneNumber(RandomStringUtils.randomNumeric(10));
        OwnerEntity savedOwner = ownerRepository.save(ownerEntity);

        VehicleEntity vehicleTransient = new VehicleEntity();
        vehicleTransient.setMake("Dacia");
        vehicleTransient.setModel("Logan");
        vehicleTransient.setVin("XMCLNDABXHY329876");
        vehicleTransient.setYear(2016);
        vehicleTransient.setLicensePlate("DCC220");
        vehicleTransient.setOwner(savedOwner);

        VehicleEntity persistedVehicle = vehicleRepository.save(vehicleTransient);

        //        GIVEN
        VehicleByIdResponse expectedVehicle = JsonReader.read("db/mocks/vehicles/vehicleById.json", VEHICLE_BY_ID_TYPE_REFERENCE);
        expectedVehicle.setOwnerId(savedOwner.getId());
        //        WHEN
        VehicleByIdResponse vehicleResponse = vehicleApiClient.getVehicleById(port, persistedVehicle.getId());

        //        THEN
        assertThat(vehicleResponse.getId()).isNotNull();

        assertThat(vehicleResponse)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id") // Ignores the 'id' field in comparison
                        .build())
                .isEqualTo(expectedVehicle);
    }

    @Test
    @DisplayName("Update vehicle")
    void updateVehicleTest() {
        // Create a vehicle to update
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setFirstName(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setLastName(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setAddress(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setPhoneNumber(RandomStringUtils.randomNumeric(10));
        OwnerEntity savedOwner = ownerRepository.save(ownerEntity);

        VehicleEntity vehicleTransient = new VehicleEntity();
        vehicleTransient.setMake("Dacia");
        vehicleTransient.setModel("Logan");
        vehicleTransient.setVin("XMCLNDABXHY329876");
        vehicleTransient.setYear(2016);
        vehicleTransient.setLicensePlate("DCC220");
        vehicleTransient.setOwner(savedOwner);

        VehicleEntity persistedVehicle = vehicleRepository.save(vehicleTransient);

        // Update request
        UpdateVehicleRequest updateRequest = new UpdateVehicleRequest();
        updateRequest.setMake("Toyota");
        updateRequest.setModel("Camry");
        updateRequest.setVin("VIN123456789");
        updateRequest.setYear(2021);
        updateRequest.setLicensePlate("XYZ 987");

        Long vehicleId = persistedVehicle.getId();

        vehicleApiClient.updateVehicle(port, vehicleId, updateRequest);

        // Fetch the updated vehicle
        VehicleByIdResponse updatedVehicle = vehicleApiClient.getVehicleById(port, vehicleId);

        // Assertions to verify the update
        assertThat(updatedVehicle.getMake()).isEqualTo("Toyota");
        assertThat(updatedVehicle.getModel()).isEqualTo("Camry");
        assertThat(updatedVehicle.getYear()).isEqualTo(2021);
        assertThat(updatedVehicle.getLicensePlate()).isEqualTo("XYZ 987");
        assertThat(updatedVehicle.getVin()).isEqualTo("VIN123456789");
    }

    @Test
    @DisplayName("Transfer Vehicle to Another Owner")
    void transferVehicleToAnotherOwnerTest() {
        // Setup initial owner and vehicle
        OwnerEntity originalOwner = new OwnerEntity("John", "Doe", "1234 Elm Street", "5551234567");
        originalOwner = ownerRepository.save(originalOwner);
        VehicleEntity vehicle = new VehicleEntity("VIN123456789", "XYZ 987", "Toyota", "Corolla", 2019);
        vehicle.setOwner(originalOwner);
        vehicle = vehicleRepository.save(vehicle);

        // Setup new owner
        OwnerEntity newOwner = new OwnerEntity("Jane", "Smith", "5678 Maple Street", "5559876543");
        newOwner = ownerRepository.save(newOwner);

        // Perform the transfer
        vehicleApiClient.transferVehicleToAnotherOwner(port, vehicle.getId(), newOwner.getId());

        // Verify the transfer
        VehicleEntity updatedVehicle = vehicleRepository.findById(vehicle.getId()).orElseThrow();
        assertThat(updatedVehicle.getOwner().getId()).isEqualTo(newOwner.getId());
    }
    @Test
    @DisplayName("Delete vehicle by id")
    void deleteVehicleTest() {
        // Setup initial owner and vehicle
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setFirstName(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setLastName(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setAddress(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setPhoneNumber(RandomStringUtils.randomNumeric(10));
        OwnerEntity savedOwner = ownerRepository.save(ownerEntity);

        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setMake("Dacia");
        vehicleEntity.setModel("Logan");
        vehicleEntity.setVin("XMCLNDABXHY329876");
        vehicleEntity.setYear(2016);
        vehicleEntity.setLicensePlate("DCC220");
        vehicleEntity.setOwner(savedOwner);
        VehicleEntity persistedVehicle = vehicleRepository.save(vehicleEntity);

        // Perform the deletion
        vehicleApiClient.deleteVehicle(port, persistedVehicle.getId());

        // Verify the vehicle is deleted
        boolean vehicleExists = vehicleRepository.existsById(persistedVehicle.getId());
        assertThat(vehicleExists).isFalse();
    }

    //        @formatter:off
    private static final TypeReference<CreateVehicleRequest> CREATE_VEHICLE_REQUEST_TYPE_REFERENCE = new TypeReference<>() {
    };
    private static final TypeReference<List<AllVehicleResponse>> ALL_VEHICLES_TYPE_REFERENCE = new TypeReference<>() {
    };
    private static final TypeReference<VehicleByIdResponse> VEHICLE_BY_ID_TYPE_REFERENCE = new TypeReference<>() {
    };
    private static final TypeReference<VehicleCreatedResponse> VEHICLE_CREATED_RESPONSE_TYPE_REFERENCE = new TypeReference<>() {
    };
    private static final TypeReference<VehicleByLPResponse> VEHICLE_BY_LICENSEPLATE_TYPE_REFERENCE = new TypeReference<>() {
    };
}
