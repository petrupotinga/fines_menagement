package com.potinga.springboot.fines_menagement.it.vehicle;

import com.fasterxml.jackson.core.type.TypeReference;
import com.potinga.springboot.fines_menagement.apiclient.VehicleApiClient;
import com.potinga.springboot.fines_menagement.common.PostgreSQLContainerInitializer;
import com.potinga.springboot.fines_menagement.common.PostgresIntegrationTest;
import com.potinga.springboot.fines_menagement.dto.rest.vehicle.AllVehicleResponse;
import com.potinga.springboot.fines_menagement.dto.rest.vehicle.CreateVehicleRequest;
import com.potinga.springboot.fines_menagement.dto.rest.vehicle.VehicleByIdResponse;
import com.potinga.springboot.fines_menagement.dto.rest.vehicle.VehicleCreatedResponse;
import com.potinga.springboot.fines_menagement.entity.OwnerEntity;
import com.potinga.springboot.fines_menagement.repository.OwnerRepository;
import com.potinga.springboot.fines_menagement.utils.JsonReader;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@PostgresIntegrationTest
class VehicleControllerTest {

    @LocalServerPort
    private String port;

    @Autowired
    private VehicleApiClient vehicleApiClient;
    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    @DisplayName("Create a new vehicle")
    void createVehicleTest() {
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setFirstName(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setLastName(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setAddress(RandomStringUtils.randomAlphabetic(10));
        ownerEntity.setPhoneNumber(RandomStringUtils.randomAlphabetic(10));
        OwnerEntity savedOwner = ownerRepository.save(ownerEntity);

        //        GIVEN
        CreateVehicleRequest createVehicleRequest = JsonReader.read("db/mocks/vehicles/createVehicleRequest.json", CREATE_VEHICLE_REQUEST_TYPE_REFERENCE);
        createVehicleRequest.setOwnerId(savedOwner.getId());

        //        WHEN
        VehicleCreatedResponse vehicleCreatedResponse = vehicleApiClient.createVehicle(port, createVehicleRequest);

        //        THEN
        VehicleCreatedResponse expectedVehicleCreatedResponse = JsonReader.read("db/mocks/vehicles/createVehicleResponse.json", VEHICLE_CREATED_RESPONSE_TYPE_REFERENCE);

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
        List<AllVehicleResponse> allVehicleResponses = JsonReader.read("db/mocks/vehicles/allVehicles.json", ALL_VEHICLES_TYPE_REFERENCE);

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
    @DisplayName("Get vehicle by id")
    void getVehicleByIdTest() {
        //        GIVEN
        VehicleByIdResponse expectedVehicle = JsonReader.read("db/mocks/vehicles/vehicleById.json", VEHICLE_BY_ID_TYPE_REFERENCE);

        //        WHEN
        VehicleByIdResponse vehicle = vehicleApiClient.getVehicleById(port, 1L);

        //        THEN
        assertThat(vehicle.getId()).isNotNull();

        assertThat(vehicle)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id") // Ignores the 'id' field in comparison
                        .build())
                .isEqualTo(expectedVehicle);
    }

    //    @formatter:off
    private static final TypeReference<CreateVehicleRequest> CREATE_VEHICLE_REQUEST_TYPE_REFERENCE = new TypeReference<>() {};
    private static final TypeReference<List<AllVehicleResponse>> ALL_VEHICLES_TYPE_REFERENCE = new TypeReference<>() {};
    private static final TypeReference<VehicleByIdResponse> VEHICLE_BY_ID_TYPE_REFERENCE = new TypeReference<>() {};
    private static final TypeReference<VehicleCreatedResponse> VEHICLE_CREATED_RESPONSE_TYPE_REFERENCE = new TypeReference<>() {};
}
