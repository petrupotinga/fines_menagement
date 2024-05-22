package com.potinga.springboot.fines_menagement;

import com.potinga.springboot.fines_menagement.common.PostgresIntegrationTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@PostgresIntegrationTest
@RequiredArgsConstructor
class FinesMenagementApplicationTests {

    private final ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        assertThat(applicationContext).isNotNull();
    }
}
