package com.potinga.springboot.fines_menagement.it.fine;

import com.potinga.springboot.fines_menagement.apiclient.FineApiClient;
import com.potinga.springboot.fines_menagement.common.PostgresIntegrationTest;
import com.potinga.springboot.fines_menagement.dto.rest.fine.*;
import com.potinga.springboot.fines_menagement.common.random.fine.RandomCreateFineRequest;
import com.potinga.springboot.fines_menagement.common.random.fine.RandomFine;
import com.potinga.springboot.fines_menagement.common.random.owner.RandomOwner;
import com.potinga.springboot.fines_menagement.common.random.vehicle.RandomVehicle;
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
        //  Given
        OwnerEntity ownerEntityTransient = RandomOwner.builder().build().get();
        OwnerEntity persistedOwnerEntity = ownerRepository.save(ownerEntityTransient);

        VehicleEntity vehicleEntityTransient = RandomVehicle.builder().build().get();
        vehicleEntityTransient.setOwner(persistedOwnerEntity);
        VehicleEntity persistedVehicleEntity = vehicleRepository.save(vehicleEntityTransient);

        CreateFineRequest createFineRequest = RandomCreateFineRequest.builder()
                .ownerId(persistedOwnerEntity.getId())
                .vehicleId(persistedVehicleEntity.getId())
                .build().get();

        // When
        FineCreatedResponse fineCreatedResponse = fineApiClient.createFine(port, createFineRequest);

        // Then
        assertThat(fineCreatedResponse)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id") // Ignores the 'id' field in comparison
                        .build())
                .isEqualTo(
                        FineCreatedResponse.builder()
                                .amount(createFineRequest.getAmount())
                                .violation(createFineRequest.getViolation())
                                .date(createFineRequest.getDate())
                                .location(createFineRequest.getLocation())
                                .ownerId(createFineRequest.getOwnerId())
                                .vehicleId(createFineRequest.getVehicleId())
                                .build()
                );
    }

    @Test
    @DisplayName("Get all fines")
    void getAllFinesTest() {
        fineRepository.deleteAll();

        //  Given
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

        // When
        List<AllFineResponse> allFinesResponse = fineApiClient.getAllFines(port);

        // Then
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
        //  Given
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

        // When
        List<AllFineResponse> allVehicleFinesResponse = fineApiClient.getAllVehicleFinesByVin(port, persistedVehicleEntity.getVin());

        // Then
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
        //  Given
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

        // When
        List<AllFineResponse> allVehicleFinesResponse = fineApiClient.getAllVehicleFinesByLicensePlate(port, persistedVehicleEntity.getLicensePlate());

        // Then
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
        //  Given
        OwnerEntity ownerEntityTransient = RandomOwner.builder().build().get();
        OwnerEntity persistedOwnerEntity = ownerRepository.save(ownerEntityTransient);

        VehicleEntity vehicleEntityTransient = RandomVehicle.builder().build().get();
        vehicleEntityTransient.setOwner(persistedOwnerEntity);
        VehicleEntity persistedVehicleEntity = vehicleRepository.save(vehicleEntityTransient);

        FineEntity fineTransient = RandomFine.builder().build().get();
        fineTransient.setOwner(persistedOwnerEntity);
        fineTransient.setVehicle(persistedVehicleEntity);

        FineEntity persistedFine = fineRepository.save(fineTransient);

        // When
        FineByIdResponse fineResponse = fineApiClient.getFineById(port, persistedFine.getId());

        // Then
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

    @Builder
    static class RandomOwner implements Supplier<OwnerEntity> {
        @Builder.Default
        private final String firstName = RandomStringUtils.randomAlphabetic(20);
        @Builder.Default
        private final String lastName = RandomStringUtils.randomAlphabetic(20);
        @Builder.Default
        private final String address = RandomStringUtils.randomAlphabetic(20);
        @Builder.Default
        private final String phoneNumber = RandomStringUtils.randomAlphabetic(20);

        @Override
        public OwnerEntity get() {
            return new OwnerEntity(firstName, lastName, address, phoneNumber);
        }
    }

    @Builder
    static class RandomVehicle implements Supplier<VehicleEntity> {

        @Builder.Default
        private String vin = RandomStringUtils.randomAlphabetic(20);
        @Builder.Default
        private String licensePlate = RandomStringUtils.randomAlphabetic(20);
        @Builder.Default
        private String make = RandomStringUtils.randomAlphabetic(20);
        @Builder.Default
        private String model = RandomStringUtils.randomAlphabetic(20);
        @Builder.Default
        private int year = Integer.parseInt(RandomStringUtils.randomNumeric(4));

        @Override
        public VehicleEntity get() {
            return new VehicleEntity(vin, licensePlate, make, model, year);
        }
    }

    @Builder
    static class RandomFine implements Supplier<FineEntity> {
        private static final Random RANDOM = new Random();

        @Builder.Default
        private double amount = RANDOM.nextDouble();
        @Builder.Default
        private String violation = RandomStringUtils.randomAlphabetic(10);
        @Builder.Default
        private String date = RandomStringUtils.randomAlphabetic(10);
        @Builder.Default
        private String location = RandomStringUtils.randomAlphabetic(10);

        @Override
        public FineEntity get() {
            return new FineEntity(amount, violation, date, location);
        }
    }
}

