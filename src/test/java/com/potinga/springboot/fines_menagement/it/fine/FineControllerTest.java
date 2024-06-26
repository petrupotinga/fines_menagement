package com.potinga.springboot.fines_menagement.it.fine;

import com.potinga.springboot.fines_menagement.apiclient.FineApiClient;
import com.potinga.springboot.fines_menagement.common.PostgresIntegrationTest;
import com.potinga.springboot.fines_menagement.dto.rest.fine.*;
import com.potinga.springboot.fines_menagement.entity.FineEntity;
import com.potinga.springboot.fines_menagement.entity.OwnerEntity;
import com.potinga.springboot.fines_menagement.entity.VehicleEntity;
import com.potinga.springboot.fines_menagement.repository.FineRepository;
import com.potinga.springboot.fines_menagement.repository.OwnerRepository;
import com.potinga.springboot.fines_menagement.repository.VehicleRepository;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@PostgresIntegrationTest
class FineControllerTest {

    @LocalServerPort
    private String port;

    @Autowired
    private FineApiClient fineApiClient;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private FineRepository fineRepository;

    @Test
    @DisplayName("Create a new fine")
    void createFineTest() {
        //        GIVEN
        OwnerEntity ownerEntityTransient = new OwnerEntity();
        ownerEntityTransient.setFirstName(RandomStringUtils.randomAlphabetic(10));
        ownerEntityTransient.setLastName(RandomStringUtils.randomAlphabetic(10));
        ownerEntityTransient.setAddress(RandomStringUtils.randomAlphabetic(10));
        ownerEntityTransient.setPhoneNumber(RandomStringUtils.randomNumeric(10));
        OwnerEntity persistedOwnerEntity = ownerRepository.save(ownerEntityTransient);

        VehicleEntity vehicleEntityTransient = new VehicleEntity();
        vehicleEntityTransient.setMake(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient.setModel(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient.setVin(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient.setYear(Integer.parseInt(RandomStringUtils.randomNumeric(4)));
        vehicleEntityTransient.setLicensePlate(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient.setOwner(persistedOwnerEntity);
        VehicleEntity persistedVehicleEntity = vehicleRepository.save(vehicleEntityTransient);

        CreateFineRequest createFineRequest = new CreateFineRequest();
        createFineRequest.setAmount(100D);
        createFineRequest.setViolation("Violation");
        createFineRequest.setDate("3 Decembrie 2023");
        createFineRequest.setLocation("Alba Iulia");
        createFineRequest.setOwnerId(persistedOwnerEntity.getId());
        createFineRequest.setVehicleId(persistedVehicleEntity.getId());

        //        WHEN
        FineCreatedResponse fineCreatedResponse = fineApiClient.createFine(port, createFineRequest);

        //        THEN
        FineCreatedResponse expectedFineCreatedResponse = new FineCreatedResponse();
        expectedFineCreatedResponse.setAmount(100D);
        expectedFineCreatedResponse.setViolation("Violation");
        expectedFineCreatedResponse.setDate("3 Decembrie 2023");
        expectedFineCreatedResponse.setLocation("Alba Iulia");
        expectedFineCreatedResponse.setOwnerId(persistedOwnerEntity.getId());
        expectedFineCreatedResponse.setVehicleId(persistedVehicleEntity.getId());

        assertThat(fineCreatedResponse)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id") // Ignores the 'id' field in comparison
                        .build())
                .isEqualTo(expectedFineCreatedResponse);
    }

    @Test
    @DisplayName("Get all fines")
    void getAllFinesTest() {
        //        GIVEN
        OwnerEntity ownerEntityTransient = new OwnerEntity();
        ownerEntityTransient.setFirstName(RandomStringUtils.randomAlphabetic(10));
        ownerEntityTransient.setLastName(RandomStringUtils.randomAlphabetic(10));
        ownerEntityTransient.setAddress(RandomStringUtils.randomAlphabetic(10));
        ownerEntityTransient.setPhoneNumber(RandomStringUtils.randomNumeric(10));
        OwnerEntity persistedOwnerEntity = ownerRepository.save(ownerEntityTransient);

        VehicleEntity vehicleEntityTransient = new VehicleEntity();
        vehicleEntityTransient.setMake(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient.setModel(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient.setVin(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient.setYear(Integer.parseInt(RandomStringUtils.randomNumeric(4)));
        vehicleEntityTransient.setLicensePlate(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient.setOwner(persistedOwnerEntity);
        VehicleEntity persistedVehicleEntity = vehicleRepository.save(vehicleEntityTransient);

        FineEntity fine1Transient = new FineEntity();
        fine1Transient.setAmount(100D);
        fine1Transient.setViolation("Violation");
        fine1Transient.setDate("3 Decembrie 2023");
        fine1Transient.setLocation("Alba Iulia");
        fine1Transient.setOwner(persistedOwnerEntity);
        fine1Transient.setVehicle(persistedVehicleEntity);

        FineEntity fine2Transient = new FineEntity();
        fine2Transient.setAmount(100D);
        fine2Transient.setViolation("Violation");
        fine2Transient.setDate("3 Decembrie 2023");
        fine2Transient.setLocation("Alba Iulia");
        fine2Transient.setOwner(persistedOwnerEntity);
        fine2Transient.setVehicle(persistedVehicleEntity);

        fineRepository.saveAll(List.of(fine1Transient, fine2Transient));

        //        WHEN
        List<AllFineResponse> allFinesResponse = fineApiClient.getAllFines(port);

        //        THEN
        AllFineResponse allFineResponse1 = new AllFineResponse();
        allFineResponse1.setAmount(100D);
        allFineResponse1.setViolation("Violation");
        allFineResponse1.setDate("3 Decembrie 2023");
        allFineResponse1.setLocation("Alba Iulia");
        allFineResponse1.setOwnerId(persistedOwnerEntity.getId());
        allFineResponse1.setVehicleId(persistedVehicleEntity.getId());

        AllFineResponse allFineResponse2 = new AllFineResponse();
        allFineResponse2.setAmount(100D);
        allFineResponse2.setViolation("Violation");
        allFineResponse2.setDate("3 Decembrie 2023");
        allFineResponse2.setLocation("Alba Iulia");
        allFineResponse2.setOwnerId(persistedOwnerEntity.getId());
        allFineResponse2.setVehicleId(persistedVehicleEntity.getId());

        allFinesResponse.forEach(fine -> assertThat(fine.getId()).isNotNull());

        assertThat(allFinesResponse)
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")  // Ignores the 'id' field in comparison
                .containsAll(List.of(allFineResponse1, allFineResponse2));
    }

    @Test
    @DisplayName("Get fine by id")
    void getFineByIdTest() {
        //        GIVEN
        OwnerEntity ownerEntityTransient = new OwnerEntity();
        ownerEntityTransient.setFirstName(RandomStringUtils.randomAlphabetic(10));
        ownerEntityTransient.setLastName(RandomStringUtils.randomAlphabetic(10));
        ownerEntityTransient.setAddress(RandomStringUtils.randomAlphabetic(10));
        ownerEntityTransient.setPhoneNumber(RandomStringUtils.randomNumeric(10));
        OwnerEntity persistedOwnerEntity = ownerRepository.save(ownerEntityTransient);

        VehicleEntity vehicleEntityTransient = new VehicleEntity();
        vehicleEntityTransient.setMake(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient.setModel(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient.setVin(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient.setYear(Integer.parseInt(RandomStringUtils.randomNumeric(4)));
        vehicleEntityTransient.setLicensePlate(RandomStringUtils.randomAlphabetic(10));
        vehicleEntityTransient.setOwner(persistedOwnerEntity);
        VehicleEntity persistedVehicleEntity = vehicleRepository.save(vehicleEntityTransient);

        FineEntity fineTransient = new FineEntity();
        fineTransient.setAmount(100D);
        fineTransient.setViolation("Violation");
        fineTransient.setDate("3 Decembrie 2023");
        fineTransient.setLocation("Alba Iulia");
        fineTransient.setOwner(persistedOwnerEntity);
        fineTransient.setVehicle(persistedVehicleEntity);

        FineEntity persistedFine = fineRepository.save(fineTransient);

        //        WHEN
        FineByIdResponse fineResponse = fineApiClient.getFineById(port, persistedFine.getId());

        //        THEN
        FineByIdResponse expectedFine = new FineByIdResponse();
        expectedFine.setAmount(100D);
        expectedFine.setViolation("Violation");
        expectedFine.setDate("3 Decembrie 2023");
        expectedFine.setLocation("Alba Iulia");
        expectedFine.setOwnerId(persistedOwnerEntity.getId());
        expectedFine.setVehicleId(persistedVehicleEntity.getId());

        assertThat(fineResponse.getId()).isNotNull();

        assertThat(fineResponse)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id") // Ignores the 'id' field in comparison
                        .build())
                .isEqualTo(expectedFine);
    }

    @Test
    @DisplayName("Update fine")
    void updateFineTest() {

        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setFirstName(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setLastName(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setAddress(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setPhoneNumber(RandomStringUtils.randomNumeric(10));
        OwnerEntity owner = ownerRepository.save(ownerEntity);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setMake(RandomStringUtils.randomAlphabetic(10));
        vehicle.setModel(RandomStringUtils.randomAlphabetic(10));
        vehicle.setVin(RandomStringUtils.randomAlphabetic(10));
        vehicle.setYear(Integer.parseInt(RandomStringUtils.randomNumeric(4)));
        vehicle.setLicensePlate(RandomStringUtils.randomAlphabetic(10));
        vehicle.setOwner(owner);
        VehicleEntity persistedVehicle = vehicleRepository.save(vehicle);

        FineEntity fine = new FineEntity();
        fine.setAmount(100D);
        fine.setViolation("Violation");
        fine.setDate("3 Decembrie 2023");
        fine.setLocation("Alba Iulia");
        fine.setOwner(owner);
        fine.setVehicle(persistedVehicle);

        FineEntity persistedFine = fineRepository.save(fine);

        UpdateFineRequest updateRequest = new UpdateFineRequest();
        updateRequest.setAmount(1000D);
        updateRequest.setViolation("Violation");
        updateRequest.setDate("5 Ianuarie 2024");
        updateRequest.setLocation("Florarii");

        Long fineId = persistedFine.getId();

        fineApiClient.updateFine(port, fineId, updateRequest);

        // Fetch the updated vehicle
        FineByIdResponse updatedfine = fineApiClient.getFineById(port, fineId);

        // Assertions to verify the update
        assertThat(updatedfine.getAmount()).isEqualTo(1000D);
        assertThat(updatedfine.getViolation()).isEqualTo("Violation");
        assertThat(updatedfine.getDate()).isEqualTo("5 Ianuarie 2024");
        assertThat(updatedfine.getLocation()).isEqualTo("Florarii");
    }
    @Test
    @DisplayName("Delete fine by id")
    void deleteFineTest() {
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setFirstName(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setLastName(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setAddress(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setPhoneNumber(RandomStringUtils.randomNumeric(10));
        OwnerEntity owner = ownerRepository.save(ownerEntity);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setMake(RandomStringUtils.randomAlphabetic(10));
        vehicle.setModel(RandomStringUtils.randomAlphabetic(10));
        vehicle.setVin(RandomStringUtils.randomAlphabetic(10));
        vehicle.setYear(Integer.parseInt(RandomStringUtils.randomNumeric(4)));
        vehicle.setLicensePlate(RandomStringUtils.randomAlphabetic(10));
        vehicle.setOwner(owner);
        VehicleEntity persistedVehicle = vehicleRepository.save(vehicle);

        FineEntity fine = new FineEntity();
        fine.setAmount(100D);
        fine.setViolation("Violation");
        fine.setDate("3 Decembrie 2023");
        fine.setLocation("Alba Iulia");
        fine.setOwner(owner);
        fine.setVehicle(persistedVehicle);

        FineEntity persistedFine = fineRepository.save(fine);
        // Perform the deletion
        fineApiClient.deleteFine(port, persistedFine.getId());

        // Verify the vehicle is deleted
        boolean fineExists = fineRepository.existsById(persistedFine.getId());
        assertThat(fineExists).isFalse();
    }
}
