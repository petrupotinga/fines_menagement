package com.potinga.springboot.fines_menagement.it.owner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.potinga.springboot.fines_menagement.apiclient.OwnerApiClient;
import com.potinga.springboot.fines_menagement.common.PostgresIntegrationTest;
import com.potinga.springboot.fines_menagement.dto.rest.owner.CreateOwnerRequest;
import com.potinga.springboot.fines_menagement.dto.rest.owner.OwnerCreatedResponse;
import com.potinga.springboot.fines_menagement.utils.JsonReader;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@PostgresIntegrationTest
public class OwnerControllerTest {

    @LocalServerPort
    private String port;

    @Autowired
    private OwnerApiClient ownerApiClient;

    @Test
    @DisplayName("Create a new owner")
    void createOwnerTest() {
        //        GIVEN
        CreateOwnerRequest createOwnerRequest = JsonReader.read("db/mocks/owner/createOwnerRequest.json", CREATE_OWNER_REQUEST_TYPE_REFERENCE);

        //        WHEN
        OwnerCreatedResponse ownerCreatedResponse = ownerApiClient.createOwner(port, createOwnerRequest);

        //        THEN
        OwnerCreatedResponse expectedOwnerCreatedResponse = JsonReader.read("db/mocks/owner/createOwnerResponse.json", OWNER_CREATED_RESPONSE_TYPE_REFERENCE);

        assertThat(ownerCreatedResponse)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id") // Ignores the 'id' field in comparison
                        .build())
                .isEqualTo(expectedOwnerCreatedResponse);
    }

    //    @formatter:off
    private static final TypeReference<CreateOwnerRequest> CREATE_OWNER_REQUEST_TYPE_REFERENCE = new TypeReference<>() {
    };
    private static final TypeReference<OwnerCreatedResponse> OWNER_CREATED_RESPONSE_TYPE_REFERENCE = new TypeReference<>() {
    };

}
