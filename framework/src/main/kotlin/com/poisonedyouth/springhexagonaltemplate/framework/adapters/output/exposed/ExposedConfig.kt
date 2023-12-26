package com.poisonedyouth.springhexagonaltemplate.framework.adapters.output.exposed

import javax.sql.DataSource
import org.jetbrains.exposed.spring.autoconfigure.ExposedAutoConfiguration
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ImportAutoConfiguration(
    value = [ExposedAutoConfiguration::class],
    exclude = [DataSourceTransactionManagerAutoConfiguration::class]
)
class ExposedConfig {

    @Bean fun databaseConfig() = DatabaseConfig { useNestedTransactions = true }

    @Bean
    fun exposedDatabaseConnection(dataSource: DataSource): Database {
        return Database.connect(dataSource)
    }
}
