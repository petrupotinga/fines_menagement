package com.potinga.springboot.fines_menagement.apiclient;

import com.potinga.springboot.fines_menagement.common.BaseRestTemplate;
import com.potinga.springboot.fines_menagement.dto.rest.fine.CreateFineRequest;
import com.potinga.springboot.fines_menagement.dto.rest.fine.FineCreatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class FineApiClient {
    private static final String SAVE_FINE = "http://localhost:{PORT}/api/v1/fines";
    private static final String GET_ALL_FINES = "http://localhost:{PORT}/api/v1/fines";
    private static final String GET_FINE_BY_ID = "http://localhost:{PORT}/api/v1/fines/{ID}";
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
}
