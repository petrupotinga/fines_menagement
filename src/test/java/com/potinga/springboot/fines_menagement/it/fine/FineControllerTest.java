package com.potinga.springboot.fines_menagement.it.fine;

import com.potinga.springboot.fines_menagement.apiclient.FineApiClient;
import com.potinga.springboot.fines_menagement.common.PostgresIntegrationTest;
import com.potinga.springboot.fines_menagement.common.random.fine.RandomCreateFineRequest;
import com.potinga.springboot.fines_menagement.common.random.fine.RandomFine;
import com.potinga.springboot.fines_menagement.common.random.owner.RandomOwner;
import com.potinga.springboot.fines_menagement.common.random.vehicle.RandomVehicle;
import com.potinga.springboot.fines_menagement.dto.rest.fine.AllFineResponse;
import com.potinga.springboot.fines_menagement.dto.rest.fine.CreateFineRequest;
import com.potinga.springboot.fines_menagement.dto.rest.fine.FineByIdResponse;
import com.potinga.springboot.fines_menagement.dto.rest.fine.FineCreatedResponse;
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
        OwnerEntity ownerEntityTransient = RandomOwner.builder().build().get();
        OwnerEntity persistedOwnerEntity = ownerRepository.save(ownerEntityTransient);

        VehicleEntity vehicleEntityTransient = RandomVehicle.builder().build().get();
        vehicleEntityTransient.setOwner(persistedOwnerEntity);
        VehicleEntity persistedVehicleEntity = vehicleRepository.save(vehicleEntityTransient);

        CreateFineRequest createFineRequest = RandomCreateFineRequest.builder()
                .ownerId(persistedOwnerEntity.getId())
                .vehicleId(persistedVehicleEntity.getId())
                .build().get();

        //        WHEN
        FineCreatedResponse fineCreatedResponse = fineApiClient.createFine(port, createFineRequest);

        //        THEN
        FineCreatedResponse expectedFineCreatedResponse = FineCreatedResponse.builder()
                .amount(createFineRequest.getAmount())
                .violation(createFineRequest.getViolation())
                .date(createFineRequest.getDate())
                .location(createFineRequest.getLocation())
                .ownerId(createFineRequest.getOwnerId())
                .vehicleId(createFineRequest.getVehicleId())
                .build();

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
        OwnerEntity ownerEntityTransient = RandomOwner.builder().build().get();
        OwnerEntity persistedOwnerEntity = ownerRepository.save(ownerEntityTransient);

        VehicleEntity vehicleEntityTransient = RandomVehicle.builder().build().get();
        vehicleEntityTransient.setOwner(persistedOwnerEntity);
        VehicleEntity persistedVehicleEntity = vehicleRepository.save(vehicleEntityTransient);

        FineEntity fine1Transient = RandomFine.builder().build().get();
        fine1Transient.setOwner(persistedOwnerEntity);
        fine1Transient.setVehicle(persistedVehicleEntity);

        FineEntity fine2Transient = RandomFine.builder().build().get();
        fine2Transient.setOwner(persistedOwnerEntity);
        fine2Transient.setVehicle(persistedVehicleEntity);

        fineRepository.saveAll(List.of(fine1Transient, fine2Transient));

        //        WHEN
        List<AllFineResponse> allFinesResponse = fineApiClient.getAllFines(port);

        //        THEN
        allFinesResponse.forEach(fine -> assertThat(fine.getId()).isNotNull());

        assertThat(allFinesResponse)
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")  // Ignores the 'id' field in comparison
                .containsAll(
                        List.of(
                                AllFineResponse.builder()
                                        .amount(fine1Transient.getAmount())
                                        .violation(fine1Transient.getViolation())
                                        .date(fine1Transient.getDate())
                                        .location(fine1Transient.getLocation())
                                        .ownerId(persistedOwnerEntity.getId())
                                        .vehicleId(persistedVehicleEntity.getId())
                                        .build(),
                                AllFineResponse.builder()
                                        .amount(fine2Transient.getAmount())
                                        .violation(fine2Transient.getViolation())
                                        .date(fine2Transient.getDate())
                                        .location(fine2Transient.getLocation())
                                        .ownerId(persistedOwnerEntity.getId())
                                        .vehicleId(persistedVehicleEntity.getId())
                                        .build()
                        )
                );
    }

    @Test
    @DisplayName("Get all fines by vehicle vin")
    void getAllVehicleFinesByVinTest() {
        //        GIVEN
        OwnerEntity ownerEntityTransient = RandomOwner.builder().build().get();
        OwnerEntity persistedOwnerEntity = ownerRepository.save(ownerEntityTransient);

        VehicleEntity vehicleEntityTransient = RandomVehicle.builder().build().get();
        vehicleEntityTransient.setOwner(persistedOwnerEntity);
        VehicleEntity persistedVehicleEntity = vehicleRepository.save(vehicleEntityTransient);

        FineEntity fine1Transient = RandomFine.builder().build().get();
        fine1Transient.setOwner(persistedOwnerEntity);
        fine1Transient.setVehicle(persistedVehicleEntity);

        FineEntity fine2Transient = RandomFine.builder().build().get();
        fine2Transient.setOwner(persistedOwnerEntity);
        fine2Transient.setVehicle(persistedVehicleEntity);

        VehicleEntity unrelatedVehicle = RandomVehicle.builder().build().get();
        unrelatedVehicle.setOwner(persistedOwnerEntity);
        VehicleEntity savedUnrelatedVehicle = vehicleRepository.save(unrelatedVehicle);

        FineEntity unrelatedFine = RandomFine.builder().build().get();
        unrelatedFine.setOwner(persistedOwnerEntity);
        unrelatedFine.setVehicle(savedUnrelatedVehicle);

        fineRepository.saveAll(List.of(fine1Transient, fine2Transient, unrelatedFine));

        //        WHEN
        List<AllFineResponse> allVehicleFinesResponse = fineApiClient.getAllVehicleFinesByVin(port, persistedVehicleEntity.getVin());

        //        THEN
        allVehicleFinesResponse.forEach(fine -> assertThat(fine.getId()).isNotNull());

        assertThat(allVehicleFinesResponse)
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")  // Ignores the 'id' field in comparison
                .containsAll(
                        List.of(
                                AllFineResponse.builder()
                                        .amount(fine1Transient.getAmount())
                                        .violation(fine1Transient.getViolation())
                                        .date(fine1Transient.getDate())
                                        .location(fine1Transient.getLocation())
                                        .ownerId(persistedOwnerEntity.getId())
                                        .vehicleId(persistedVehicleEntity.getId())
                                        .build(),
                                AllFineResponse.builder()
                                        .amount(fine2Transient.getAmount())
                                        .violation(fine2Transient.getViolation())
                                        .date(fine2Transient.getDate())
                                        .location(fine2Transient.getLocation())
                                        .ownerId(persistedOwnerEntity.getId())
                                        .vehicleId(persistedVehicleEntity.getId())
                                        .build()
                        )
                );
    }

    @Test
    @DisplayName("Get all fines by vehicle licensePlate")
    void getAllVehicleFinesByLicensePlateTest() {
        //        GIVEN
        OwnerEntity ownerEntityTransient = RandomOwner.builder().build().get();
        OwnerEntity persistedOwnerEntity = ownerRepository.save(ownerEntityTransient);

        VehicleEntity vehicleEntityTransient = RandomVehicle.builder().build().get();
        vehicleEntityTransient.setOwner(persistedOwnerEntity);
        VehicleEntity persistedVehicleEntity = vehicleRepository.save(vehicleEntityTransient);

        FineEntity fine1Transient = RandomFine.builder().build().get();
        fine1Transient.setOwner(persistedOwnerEntity);
        fine1Transient.setVehicle(persistedVehicleEntity);

        FineEntity fine2Transient = RandomFine.builder().build().get();
        fine2Transient.setOwner(persistedOwnerEntity);
        fine2Transient.setVehicle(persistedVehicleEntity);

        VehicleEntity unrelatedVehicle = RandomVehicle.builder().build().get();
        unrelatedVehicle.setOwner(persistedOwnerEntity);
        VehicleEntity savedUnrelatedVehicle = vehicleRepository.save(unrelatedVehicle);

        FineEntity unrelatedFine = RandomFine.builder().build().get();
        unrelatedFine.setOwner(persistedOwnerEntity);
        unrelatedFine.setVehicle(savedUnrelatedVehicle);

        fineRepository.saveAll(List.of(fine1Transient, fine2Transient, unrelatedFine));

        //        WHEN
        List<AllFineResponse> allVehicleFinesResponse = fineApiClient.getAllVehicleFinesByLicensePlate(port, persistedVehicleEntity.getLicensePlate());

        //        THEN
        allVehicleFinesResponse.forEach(fine -> assertThat(fine.getId()).isNotNull());

        assertThat(allVehicleFinesResponse)
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")  // Ignores the 'id' field in comparison
                .containsAll(
                        List.of(
                                AllFineResponse.builder()
                                        .amount(fine1Transient.getAmount())
                                        .violation(fine1Transient.getViolation())
                                        .date(fine1Transient.getDate())
                                        .location(fine1Transient.getLocation())
                                        .ownerId(persistedOwnerEntity.getId())
                                        .vehicleId(persistedVehicleEntity.getId())
                                        .build(),
                                AllFineResponse.builder()
                                        .amount(fine2Transient.getAmount())
                                        .violation(fine2Transient.getViolation())
                                        .date(fine2Transient.getDate())
                                        .location(fine2Transient.getLocation())
                                        .ownerId(persistedOwnerEntity.getId())
                                        .vehicleId(persistedVehicleEntity.getId())
                                        .build()
                        )
                );
    }

    @Test
    @DisplayName("Get fine by id")
    void getFineByIdTest() {
        //        GIVEN
        OwnerEntity ownerEntityTransient = RandomOwner.builder().build().get();
        OwnerEntity persistedOwnerEntity = ownerRepository.save(ownerEntityTransient);

        VehicleEntity vehicleEntityTransient = RandomVehicle.builder().build().get();
        vehicleEntityTransient.setOwner(persistedOwnerEntity);
        VehicleEntity persistedVehicleEntity = vehicleRepository.save(vehicleEntityTransient);

        FineEntity fineTransient = RandomFine.builder().build().get();
        fineTransient.setOwner(persistedOwnerEntity);
        fineTransient.setVehicle(persistedVehicleEntity);

        FineEntity persistedFine = fineRepository.save(fineTransient);

        //        WHEN
        FineByIdResponse fineResponse = fineApiClient.getFineById(port, persistedFine.getId());

        //        THEN
        FineByIdResponse expectedFine = FineByIdResponse.builder()
                .amount(persistedFine.getAmount())
                .violation(persistedFine.getViolation())
                .date(persistedFine.getDate())
                .location(persistedFine.getLocation())
                .ownerId(persistedOwnerEntity.getId())
                .vehicleId(persistedVehicleEntity.getId())
                .build();

        assertThat(fineResponse.getId()).isNotNull();

        assertThat(fineResponse)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id") // Ignores the 'id' field in comparison
                        .build())
                .isEqualTo(expectedFine);
    }
}

