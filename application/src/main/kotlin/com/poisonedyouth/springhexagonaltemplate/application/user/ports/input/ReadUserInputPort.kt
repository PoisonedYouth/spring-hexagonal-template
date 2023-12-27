package com.poisonedyouth.springhexagonaltemplate.application.user.ports.input

import com.poisonedyouth.springhexagonaltemplate.application.user.ports.output.UserOutputPort
import com.poisonedyouth.springhexagonaltemplate.application.user.usecases.ReadUserUseCase
import com.poisonedyouth.springhexagonaltemplate.common.vo.Identity
import com.poisonedyouth.springhexagonaltemplate.domain.user.entity.User

class ReadUserInputPort(private val userOutputPort: UserOutputPort) : ReadUserUseCase {
    override fun find(userId: Identity): User? {
        return userOutputPort.findBy(userId)
    }

    override fun all(): List<User> {
        return userOutputPort.all()
    }
}
