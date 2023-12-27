package com.poisonedyouth.springhexagonaltemplate.application.user.ports.input

import com.poisonedyouth.springhexagonaltemplate.application.user.ports.output.UserOutputPort
import com.poisonedyouth.springhexagonaltemplate.application.user.usecases.WriteUserUseCase
import com.poisonedyouth.springhexagonaltemplate.common.exception.NotFoundException
import com.poisonedyouth.springhexagonaltemplate.common.vo.Identity
import com.poisonedyouth.springhexagonaltemplate.domain.user.entity.User

class WriteUserInputPort(
    private val userOutputPort: UserOutputPort,
) : WriteUserUseCase {
    override fun add(user: User): Identity {
        return userOutputPort.store(user)
    }

    override fun update(user: User) {
        requireNotNull(userOutputPort.findBy(user.identity)) {
            "User with id '${user.identity} does not exist."
        }
        userOutputPort.store(user)
    }

    override fun delete(userId: Identity) {
        userOutputPort.findBy(userId)
            ?: throw NotFoundException("User with id '${userId.getId()} does not exist.")
        userOutputPort.delete(userId)
    }
}
