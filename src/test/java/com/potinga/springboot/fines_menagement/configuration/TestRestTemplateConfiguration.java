package com.potinga.springboot.fines_menagement.configuration;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestRestTemplateConfiguration {

    @Bean
    public TestRestTemplate testRestTemplate() {
        return new TestRestTemplate();
    }
}
