package com.potinga.springboot.fines_menagement.apiclient;

import com.potinga.springboot.fines_menagement.common.BaseRestTemplate;
import com.potinga.springboot.fines_menagement.dto.rest.vehicle.AllVehicleResponse;
import com.potinga.springboot.fines_menagement.dto.rest.vehicle.CreateVehicleRequest;
import com.potinga.springboot.fines_menagement.dto.rest.vehicle.VehicleByLPResponse;
import com.potinga.springboot.fines_menagement.dto.rest.vehicle.VehicleCreatedResponse;
import com.potinga.springboot.fines_menagement.dto.rest.vehicle.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class VehicleApiClient {

    private static final String SAVE_VEHICLE = "http://localhost:{PORT}/api/v1/vehicles";
    private static final String GET_ALL_VEHICLES = "http://localhost:{PORT}/api/v1/vehicles";
    private static final String GET_VEHICLE_BY_ID = "http://localhost:{PORT}/api/v1/vehicles/{ID}";
    private static final String GET_VEHICLE_BY_LICENSEPLATE = "http://localhost:{PORT}/api/v1/vehicles/{LICENSEPLATE}";

    private static final String UPDATE_VEHICLE_BY_ID = "http://localhost:{PORT}/api/v1/vehicles/{ID}";
    private static final String TRANSFER_VEHICLE_TO_ANOTHER_OWNER = "http://localhost:{PORT}/api/v1/vehicles/{VEHICLE_ID}/transfer?newOwnerId={NEW_OWNER_ID}";
    private static final String DELETE_VEHICLE_BY_ID = "http://localhost:{PORT}/api/v1/vehicles/{ID}";

    @Autowired
    private BaseRestTemplate baseRestTemplate;

    public VehicleCreatedResponse createVehicle(String port, CreateVehicleRequest request) {
        String realUrl = SAVE_VEHICLE.replace("{PORT}", port);
        var response = baseRestTemplate.exchange(
                RequestEntity.post(realUrl)
                        .contentType(APPLICATION_JSON)
                        .body(request),
                new ParameterizedTypeReference<VehicleCreatedResponse>() {}
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        return response.getBody();
    }

    public List<AllVehicleResponse> getAllVehicles(String port) {
        var response = baseRestTemplate.exchange(
                RequestEntity.get(GET_ALL_VEHICLES.replace("{PORT}", port)).build(),
                new ParameterizedTypeReference<List<AllVehicleResponse>>() {
                }
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        return response.getBody();
    }

    //    public VehicleByIdResponse getVehicleById(String port, Long id) {
//        var response = baseRestTemplate.exchange(
//                RequestEntity.get(GET_VEHICLE_BY_ID
//                        .replace("{PORT}", port)
//                        .replace("{ID}", id.toString())
//                ).build(),
//                new ParameterizedTypeReference<VehicleByIdResponse>() {}
//        );
//
//        assertThat(response).isNotNull();
//        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
//
//        return response.getBody();
//    }
    public VehicleByLPResponse getVehicleByLicensePlate(String port, String licensePlate) {
        var response = baseRestTemplate.exchange(
                RequestEntity.get(GET_VEHICLE_BY_LICENSEPLATE
                        .replace("{PORT}", port)
                        .replace("{LICENSEPLATE}", licensePlate)).build(),
                new ParameterizedTypeReference<VehicleByLPResponse>() {
                }
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        return response.getBody();
    }

    public UpdateVehicleResponse updateVehicle(String port, Long id, UpdateVehicleRequest updateRequest) {
        var response = baseRestTemplate.exchange(
                RequestEntity.put(UPDATE_VEHICLE_BY_ID
                                .replace("{PORT}", port)
                                .replace("{ID}", id.toString())
                        ).contentType(APPLICATION_JSON)
                        .body(updateRequest),
                new ParameterizedTypeReference<UpdateVehicleResponse>() {}
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        return response.getBody();
    }

    public void transferVehicleToAnotherOwner(String port, Long vehicleId, Long newOwnerId) {
        String URL = TRANSFER_VEHICLE_TO_ANOTHER_OWNER
                .replace("{PORT}", port)
                .replace("{VEHICLE_ID}", vehicleId.toString())
                .replace("{NEW_OWNER_ID}", newOwnerId.toString());
        var response = baseRestTemplate.exchange(
                RequestEntity.post(URL).build(),
                new ParameterizedTypeReference<Void>() {}
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }
}
