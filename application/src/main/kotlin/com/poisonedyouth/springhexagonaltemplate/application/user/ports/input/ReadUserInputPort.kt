package com.poisonedyouth.springhexagonaltemplate.application.user.ports.input

import com.poisonedyouth.springhexagonaltemplate.application.user.ports.output.UserOutputPort
import com.poisonedyouth.springhexagonaltemplate.application.user.usecases.ReadUserUseCase
import com.poisonedyouth.springhexagonaltemplate.common.vo.Identity
import java.util.*

class ReadUserInputPort(private val userOutputPort: UserOutputPort) : ReadUserUseCase {
    override fun find(userId: UUID): UserDto? {
        return userOutputPort.findBy(Identity.UUIDIdentity(userId))?.toUserDto()
    }

    override fun all(): List<UserDto> {
        return userOutputPort.all().map { it.toUserDto() }
    }
}
