package com.poisonedyouth.springhexagonaltemplate.bootstrap

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

internal class KPostgreSQLContainer(image: String) :
    PostgreSQLContainer<KPostgreSQLContainer>(image)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class SpringHexagonalTemplateApplicationTest {
    companion object {
        @Container var postgres: PostgreSQLContainer<*> = KPostgreSQLContainer("postgres:16-alpine")

        @DynamicPropertySource
        @JvmStatic
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("datasource.hikari.driverClassName", postgres::getDriverClassName)
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.password", postgres::getPassword)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.flyway.url", postgres::getJdbcUrl)
            registry.add("spring.flyway.user", postgres::getUsername)
            registry.add("spring.flyway.password", postgres::getPassword)
        }
    }

    @Test
    fun contextLoads() {
        // Check that application can start successful
    }
}
