package com.poisonedyouth.springhexagonaltemplate.application.user.usecases

import com.poisonedyouth.springhexagonaltemplate.application.user.ports.input.NewUserDto
import com.poisonedyouth.springhexagonaltemplate.application.user.ports.input.UserDto
import java.util.*

interface WriteUserUseCase {
    fun add(user: NewUserDto): UUID

    fun update(user: UserDto)

    fun delete(userId: UUID)
}
