package com.potinga.springboot.fines_menagement.common;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.HashMap;
import java.util.Map;

public class PostgreSQLContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    public PostgreSQLContainerInitializer() {
        postgreSQLContainer.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        Map<String, Object> values = new HashMap<>();
        values.put("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
        values.put("spring.datasource.password", postgreSQLContainer.getPassword());
        values.put("spring.datasource.username", postgreSQLContainer.getUsername());

        applicationContext.getEnvironment().getPropertySources().addFirst(new MapPropertySource("testcontainers", values));
    }

    public static Integer getMappedPort() {
        return postgreSQLContainer.getMappedPort(5432);
    }
}
