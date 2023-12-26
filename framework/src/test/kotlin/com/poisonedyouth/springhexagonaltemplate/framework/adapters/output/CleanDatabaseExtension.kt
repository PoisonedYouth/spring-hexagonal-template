package com.poisonedyouth.springhexagonaltemplate.framework.adapters.output

import org.flywaydb.core.Flyway
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension

@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@ExtendWith(ClearDatabaseExtension::class)
@TestPropertySource(properties = ["spring.flyway.clean-disabled=false"])
annotation class ClearDatabase

class ClearDatabaseExtension : BeforeEachCallback {
    override fun beforeEach(extensionContext: ExtensionContext) {
        val flyway =
            SpringExtension.getApplicationContext(extensionContext).getBean(Flyway::class.java)
        flyway.clean()
        flyway.migrate()
    }
}
