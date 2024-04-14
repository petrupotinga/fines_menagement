package com.potinga.springboot.fines_menagement.common;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public final class BaseRestTemplate {

    private final TestRestTemplate restTemplate;

    public BaseRestTemplate(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> ResponseEntity<T> exchange(RequestEntity<?> requestEntity, ParameterizedTypeReference<T> responseType) {
        return this.restTemplate.exchange(requestEntity, responseType);
    }
}
