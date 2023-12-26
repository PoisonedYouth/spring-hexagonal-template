package com.poisonedyouth.springhexagonaltemplate.framework.adapters.output

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container

@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@ContextConfiguration(initializers = [TestcontainersInitializer::class])
annotation class EnableTestcontainers

class TestcontainersInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Container
    var postgres: PostgreSQLContainer<*> =
        KPostgreSQLContainer("postgres:16-alpine").apply { this.start() }

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        TestPropertyValues.of(
                "spring.datasource.driverClassName=" + postgres.driverClassName,
                "spring.datasource.url=" + postgres.getJdbcUrl(),
                "spring.datasource.username=" + postgres.username,
                "spring.datasource.password=" + postgres.password,
                "spring.flyway.url=" + postgres.getJdbcUrl(),
                "spring.flyway.user=" + postgres.username,
                "spring.flyway.password=" + postgres.password
            )
            .applyTo(applicationContext.environment)
    }
}
