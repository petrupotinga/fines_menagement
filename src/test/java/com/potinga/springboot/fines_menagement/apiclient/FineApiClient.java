package com.potinga.springboot.fines_menagement.apiclient;

import com.potinga.springboot.fines_menagement.common.BaseRestTemplate;
import com.potinga.springboot.fines_menagement.dto.rest.fine.AllFineResponse;
import com.potinga.springboot.fines_menagement.dto.rest.fine.CreateFineRequest;
import com.potinga.springboot.fines_menagement.dto.rest.fine.FineByIdResponse;
import com.potinga.springboot.fines_menagement.dto.rest.fine.FineCreatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class FineApiClient {
    private static final String SAVE_FINE = "http://localhost:{PORT}/api/v1/fines";
    private static final String GET_ALL_FINES = "http://localhost:{PORT}/api/v1/fines";
    private static final String GET_FINE_BY_ID = "http://localhost:{PORT}/api/v1/fines/{ID}";
    private static final String GET_VEHICLE_FINES_BY_VIN = "http://localhost:{PORT}/api/v1/fines/vehicleVin/{VIN}";
    private static final String GET_VEHICLE_FINES_BY_LICENSE_PLATE = "http://localhost:{PORT}/api/v1/fines/vehicleLicensePlate/{LICENSE_PLATE}";
    private static final String UPDATE_FINE_BY_ID = "http://localhost:{PORT}/api/v1/fines/{ID}";
    private static final String DELETE_FINE_BY_ID = "http://localhost:{PORT}/api/v1/fines/{ID}";

    @Autowired
    private BaseRestTemplate baseRestTemplate;

    public FineCreatedResponse createFine(String port, CreateFineRequest request) {
        var response = baseRestTemplate.exchange(
                RequestEntity.post(SAVE_FINE.replace("{PORT}", port))
                        .contentType(APPLICATION_JSON)
                        .body(request),
                new ParameterizedTypeReference<FineCreatedResponse>() {
                }
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        return response.getBody();
    }

    public List<AllFineResponse> getAllFines(String port) {
        var response = baseRestTemplate.exchange(
                RequestEntity.get(GET_ALL_FINES.replace("{PORT}", port)).build(),
                new ParameterizedTypeReference<List<AllFineResponse>>() {
                }
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        return response.getBody();
    }

    public FineByIdResponse getFineById(String port, Long id) {
        var response = baseRestTemplate.exchange(
                RequestEntity.get(GET_FINE_BY_ID
                        .replace("{PORT}", port)
                        .replace("{ID}", id.toString())
                ).build(),
                new ParameterizedTypeReference<FineByIdResponse>() {
                }
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        return response.getBody();
    }

    public List<AllFineResponse> getAllVehicleFinesByVin(String port, String vin) {
        var response = baseRestTemplate.exchange(
                RequestEntity.get(GET_VEHICLE_FINES_BY_VIN
                        .replace("{PORT}", port)
                        .replace("{VIN}", vin)
                ).build(),
                new ParameterizedTypeReference<List<AllFineResponse>>() {
                }
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        return response.getBody();
    }

    public List<AllFineResponse> getAllVehicleFinesByLicensePlate(String port, String licensePlate) {
        var response = baseRestTemplate.exchange(
                RequestEntity.get(GET_VEHICLE_FINES_BY_LICENSE_PLATE
                        .replace("{PORT}", port)
                        .replace("{LICENSE_PLATE}", licensePlate)
                ).build(),
                new ParameterizedTypeReference<List<AllFineResponse>>() {
                }
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        return response.getBody();
    }
}
