package com.poisonedyouth.springhexagonaltemplate.application.user.usecases

import com.poisonedyouth.springhexagonaltemplate.application.user.ports.input.UserDto
import java.util.*

interface ReadUserUseCase {
    fun find(userId: UUID): UserDto?

    fun all(): List<UserDto>
}
