package com.potinga.springboot.fines_menagement.common;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Target(TYPE)
@Retention(RUNTIME)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ContextConfiguration(initializers = PostgreSQLContainerInitializer.class)
@AutoConfigureTestDatabase(replace = NONE)
@ActiveProfiles("test")
public @interface PostgresIntegrationTest {
}
