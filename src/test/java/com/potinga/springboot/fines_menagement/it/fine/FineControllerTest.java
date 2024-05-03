package com.potinga.springboot.fines_menagement.it.fine;

import com.fasterxml.jackson.core.type.TypeReference;
import com.potinga.springboot.fines_menagement.apiclient.FineApiClient;
import com.potinga.springboot.fines_menagement.common.PostgresIntegrationTest;
import com.potinga.springboot.fines_menagement.dto.rest.fine.CreateFineRequest;
import com.potinga.springboot.fines_menagement.dto.rest.fine.FineCreatedResponse;
import com.potinga.springboot.fines_menagement.entity.FineEntity;
import com.potinga.springboot.fines_menagement.entity.OwnerEntity;
import com.potinga.springboot.fines_menagement.entity.VehicleEntity;
import com.potinga.springboot.fines_menagement.repository.FineRepository;
import com.potinga.springboot.fines_menagement.repository.OwnerRepository;
import com.potinga.springboot.fines_menagement.repository.VehicleRepository;
import com.potinga.springboot.fines_menagement.utils.JsonReader;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

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

//    @Test
//    @DisplayName("Get all fines")
//    void getAllFinesTest() {
//        OwnerEntity ownerEntity = new OwnerEntity();
//        ownerEntity.setFirstName(RandomStringUtils.randomAlphabetic(10));
//        ownerEntity.setLastName(RandomStringUtils.randomAlphabetic(10));
//        ownerEntity.setAddress(RandomStringUtils.randomAlphabetic(10));
//        ownerEntity.setPhoneNumber(RandomStringUtils.randomNumeric(10));
//        OwnerEntity savedOwner = ownerRepository.save(ownerEntity);
//
//        FineEntity fine1 = new FineEntity();
//        fine1.setMake("Dacia");
//        fine1.setModel("Logan");
//        fine1.setVin("XMCLNDABXHY329876");
//        fine1.setYear(2016);
//        fine1.setLicensePlate("DCC220");
//        fine1.setOwner(savedOwner);
//
//        FineEntity fine2 = new FineEntity();
//        fine2.setMake("Mercedes");
//        fine2.setModel("E220");
//        fine2.setVin("PSALNDABXHY329712");
//        fine2.setYear(2018);
//        fine2.setLicensePlate("ACD321");
//        fine2.setOwner(savedOwner);
//
//        fineRepository.saveAll(List.of(fine1, fine2));
//
//        //        GIVEN
//        List<AllFineResponse> allFineResponses = JsonReader.read("db/mocks/fines/allFines.json", ALL_VEHICLES_TYPE_REFERENCE);
//
//        //        WHEN
//        List<AllFineResponse> allFines = fineApiClient.getAllFines(port);
//
//        //        THEN
//        allFines.forEach(fine -> assertThat(fine.getId()).isNotNull());
//
//        assertThat(allFines)
//                .hasSize(2)
//                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")  // Ignores the 'id' field in comparison
//                .containsAll(allFineResponses);
//    }
//
//    @Test
//    @DisplayName("Get fine by id")
//    void getFineByIdTest() {
//        OwnerEntity ownerEntity = new OwnerEntity();
//        ownerEntity.setFirstName(RandomStringUtils.randomAlphabetic(10));
//        ownerEntity.setLastName(RandomStringUtils.randomAlphabetic(10));
//        ownerEntity.setAddress(RandomStringUtils.randomAlphabetic(10));
//        ownerEntity.setPhoneNumber(RandomStringUtils.randomNumeric(10));
//        OwnerEntity savedOwner = ownerRepository.save(ownerEntity);
//
//        FineEntity fineTransient = new FineEntity();
//        fineTransient.setMake("Dacia");
//        fineTransient.setModel("Logan");
//        fineTransient.setVin("XMCLNDABXHY329876");
//        fineTransient.setYear(2016);
//        fineTransient.setLicensePlate("DCC220");
//        fineTransient.setOwner(savedOwner);
//
//        FineEntity persistedFine = fineRepository.save(fineTransient);
//
//        //        GIVEN
//        FineByIdResponse expectedFine = JsonReader.read("db/mocks/fines/fineById.json", VEHICLE_BY_ID_TYPE_REFERENCE);
//
//        //        WHEN
//        FineByIdResponse fineResponse = fineApiClient.getFineById(port, persistedFine.getId());
//
//        //        THEN
//        assertThat(fineResponse.getId()).isNotNull();
//
//        assertThat(fineResponse)
//                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
//                        .withIgnoredFields("id") // Ignores the 'id' field in comparison
//                        .build())
//                .isEqualTo(expectedFine);
//    }

    //    @formatter:off
    private static final TypeReference<CreateFineRequest> CREATE_VEHICLE_REQUEST_TYPE_REFERENCE = new TypeReference<>() {};
    private static final TypeReference<FineCreatedResponse> VEHICLE_CREATED_RESPONSE_TYPE_REFERENCE = new TypeReference<>() {};
//    private static final TypeReference<List<AllFineResponse>> ALL_VEHICLES_TYPE_REFERENCE = new TypeReference<>() {};
//    private static final TypeReference<FineByIdResponse> VEHICLE_BY_ID_TYPE_REFERENCE = new TypeReference<>() {};
}
