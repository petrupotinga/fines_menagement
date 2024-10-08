package com.potinga.springboot.fines_menagement.apiclient;

import com.potinga.springboot.fines_menagement.common.BaseRestTemplate;
import com.potinga.springboot.fines_menagement.dto.rest.fine.*;
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
    private static final String GET_VEHICLE_FINES_BY_LICENSE_PLATE = "http://localhost:{PORT}/api/v1/fines/vehicle/{LICENSE_PLATE}";
    private static final String UPDATE_FINE_BY_ID = "http://localhost:{PORT}/api/v1/fines/{ID}";
    private static final String DELETE_FINE_BY_ID = "http://localhost:{PORT}/api/v1/fines/{ID}";
    private static final String GET_FINE_STATISTICS = "http://localhost:{PORT}/api/v1/fines/statistics";
    private static final String GET_FINE_STATISTICS_BY_VIOLATION = "http://localhost:{PORT}/api/v1/fines/statistics/{VIOLATION}";



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
    public void updateFine(String port, Long id, UpdateFineRequest updateRequest) {
        var response = baseRestTemplate.exchange(
                RequestEntity.put(UPDATE_FINE_BY_ID
                                .replace("{PORT}", port)
                                .replace("{ID}", id.toString())
                        ).contentType(APPLICATION_JSON)
                        .body(updateRequest),
                new ParameterizedTypeReference<UpdateFineResponse>() {
                }
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    public void deleteFine(String port, Long id) {
        String URL = DELETE_FINE_BY_ID
                .replace("{PORT}", port)
                .replace("{ID}", id.toString());
        var response = baseRestTemplate.exchange(
                RequestEntity.delete(URL).build(),
                new ParameterizedTypeReference<Void>() {
                }
        );
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    public FineStatisticsResponse getFineStatistics(String port) {
        var response = baseRestTemplate.exchange(
                RequestEntity.get(GET_FINE_STATISTICS.replace("{PORT}", port)).build(),
                new ParameterizedTypeReference<FineStatisticsResponse>() {
                }
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        return response.getBody();
    }
    public FineStatisticsResponse getFineStatisticsByViolation(String port, String violation) {
        var response = baseRestTemplate.exchange(
                RequestEntity.get(GET_FINE_STATISTICS_BY_VIOLATION
                                .replace("{PORT}", port)
                                .replace("{VIOLATION}", violation))
                        .build(),
                new ParameterizedTypeReference<FineStatisticsResponse>() {
                }
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        return response.getBody();
    }
}
