package com.potinga.springboot.fines_menagement.apiclient;

import com.potinga.springboot.fines_menagement.common.BaseRestTemplate;
import com.potinga.springboot.fines_menagement.dto.rest.owner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class OwnerApiClient {

    private static final String SAVE_OWNER = "http://localhost:{PORT}/api/v1/owners";
    private static final String GET_ALL_OWNERS = "http://localhost:{PORT}/api/v1/owners";
    private static final String GET_OWNER_BY_ID = "http://localhost:{PORT}/api/v1/owners/{ID}";
    private static final String GET_OWNER_BY_IDNP = "http://localhost:{PORT}/api/v1/owners/idnp/{IDNP}";
    private static final String UPDATE_OWNER_BY_ID = "http://localhost:{PORT}/api/v1/owners/{ID}";
    private static final String DELETE_OWNER_BY_ID = "http://localhost:{PORT}/api/v1/owners/{ID}";

    @Autowired
    private BaseRestTemplate baseRestTemplate;

    public OwnerCreatedResponse createOwner(String port, CreateOwnerRequest request) {
        var response = baseRestTemplate.exchange(
                RequestEntity.post(SAVE_OWNER.replace("{PORT}", port))
                        .contentType(APPLICATION_JSON)
                        .body(request),
                new ParameterizedTypeReference<OwnerCreatedResponse>() {
                }
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        return response.getBody();
    }

    public List<AllOwnerResponse> getAllOwners(String port) {
        var response = baseRestTemplate.exchange(
                RequestEntity.get(GET_ALL_OWNERS.replace("{PORT}", port)).build(),
                new ParameterizedTypeReference<List<AllOwnerResponse>>() {
                }
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        return response.getBody();
    }

    public OwnerByIdResponse getOwnerById(String port, Long id) {
        var response = baseRestTemplate.exchange(
                RequestEntity.get(GET_OWNER_BY_ID
                        .replace("{PORT}", port)
                        .replace("{ID}", id.toString())
                ).build(),
                new ParameterizedTypeReference<OwnerByIdResponse>() {
                }
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        return response.getBody();
    }
    public OwnerByIdnpResponse getOwnerByIdnp(String port, String idnp) {
        var response = baseRestTemplate.exchange(
                RequestEntity.get(GET_OWNER_BY_IDNP
                        .replace("{PORT}", port)
                        .replace("{IDNP}", idnp)
                ).build(),
                new ParameterizedTypeReference<OwnerByIdnpResponse>() {
                }
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        return response.getBody();
    }
    public UpdateOwnerResponse updateOwner(String port, Long id, UpdateOwnerRequest request) {
        var response = baseRestTemplate.exchange(
                RequestEntity.put(UPDATE_OWNER_BY_ID
                                .replace("{PORT}", port)
                                .replace("{ID}", id.toString()))
                        .contentType(APPLICATION_JSON)
                        .body(request),
                new ParameterizedTypeReference<UpdateOwnerResponse>() {
                }
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        return response.getBody();
    }
    public void deleteOwner(String port, Long id) {
        String URL = DELETE_OWNER_BY_ID
                .replace("{PORT}", port)
                .replace("{ID}", id.toString());
        var response = baseRestTemplate.exchange(
                RequestEntity.delete(URL).build(),
                new ParameterizedTypeReference<Void>() {}
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }
}
