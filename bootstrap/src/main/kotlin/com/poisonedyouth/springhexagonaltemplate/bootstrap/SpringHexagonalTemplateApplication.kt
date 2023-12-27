package com.poisonedyouth.springhexagonaltemplate.bootstrap

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@OpenAPIDefinition(
    info =
        Info(
            title = "SpringHexagonalTemplate API",
            version = "0.0.1",
            description = "Sample Hexagonal Application"
        )
)
@SecurityScheme(
    name = "springhexagonaltemplate",
    scheme = "basic",
    type = SecuritySchemeType.HTTP,
    `in` = SecuritySchemeIn.HEADER
)
@ComponentScan(basePackages = ["com.poisonedyouth.springhexagonaltemplate.framework"])
class SpringHexagonalTemplateApplication

fun main(args: Array<String>) {
    runApplication<SpringHexagonalTemplateApplication>(*args)
}
