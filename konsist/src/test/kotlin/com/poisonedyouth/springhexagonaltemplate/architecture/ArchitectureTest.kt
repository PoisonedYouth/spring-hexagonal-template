package com.poisonedyouth.springhexagonaltemplate.architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import com.lemonappdev.konsist.api.ext.list.imports
import com.lemonappdev.konsist.api.ext.list.withPackage
import com.lemonappdev.konsist.api.verify.assertFalse
import org.junit.jupiter.api.Test

class ArchitectureTest {

    @Test
    fun `dependencies of modules are correct`() {
        Konsist.scopeFromProduction().assertArchitecture {
            val applicationLayer =
                Layer("application", "com.poisonedyouth.springhexagonaltemplate.application..")
            val domain = Layer("domain", "com.poisonedyouth.springhexagonaltemplate.domain..")
            val framework =
                Layer("framework", "com.poisonedyouth.springhexagonaltemplate.framework..")
            val bootstrap =
                Layer("bootstrap", "com.poisonedyouth.springhexagonaltemplate.bootstrap..")
            val common = Layer("common", "com.poisonedyouth.springhexagonaltemplate.common..")

            common.dependsOnNothing()
            domain.dependsOn(common)
            applicationLayer.dependsOn(domain, common)
            framework.dependsOn(applicationLayer, domain, common)
            bootstrap.dependsOn(framework)
        }
    }

    @Test
    fun `domain layer does not use Spring annotations`() {
        Konsist.scopeFromProduction()
            .files
            .withPackage("com.poisonedyouth.springhexagonaltemplate.domain..")
            .imports
            .assertFalse { import -> import.name.startsWith("org.springframework.") }
    }

    @Test
    fun `application player does not use framework layer`() {
        Konsist.scopeFromProduction()
            .files
            .withPackage("com.poisonedyouth.springhexagonaltemplate.application..")
            .imports
            .assertFalse { import ->
                import.name.startsWith("com.poisonedyouth.springhexagonaltemplate.framework")
            }
    }
}
