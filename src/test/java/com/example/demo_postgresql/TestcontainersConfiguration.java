package com.example.demo_postgresql;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {
    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        var postgresContainer = new PostgreSQLContainer<>("postgres:15")
                .withDatabaseName("test_db")
                .withUsername("test")
                .withPassword("test")
                .withEnv("TZ", "Europe/Kyiv"); // Set timezone;
        return postgresContainer;
    }
}
