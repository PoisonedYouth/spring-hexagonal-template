package com.poisonedyouth.springhexagonaltemplate.framework.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.poisonedyouth.springhexagonaltemplate.application.user.ports.input.ReadUserInputPort
import com.poisonedyouth.springhexagonaltemplate.application.user.ports.input.WriteUserInputPort
import com.poisonedyouth.springhexagonaltemplate.application.user.ports.output.UserOutputPort
import com.poisonedyouth.springhexagonaltemplate.application.user.usecases.ReadUserUseCase
import com.poisonedyouth.springhexagonaltemplate.application.user.usecases.WriteUserUseCase
import com.poisonedyouth.springhexagonaltemplate.framework.adapters.output.exposed.ExposedUserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfiguration {

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper().registerModules(KotlinModule.Builder().build(), JavaTimeModule())
    }

    @Bean fun userOutputPort(): UserOutputPort = ExposedUserRepository()

    @Bean
    fun writeUserUseCase(
        userOutputPort: UserOutputPort,
    ): WriteUserUseCase = WriteUserInputPort(userOutputPort)

    @Bean
    fun readUserUseCase(userOutputPort: UserOutputPort): ReadUserUseCase =
        ReadUserInputPort(userOutputPort)
}
