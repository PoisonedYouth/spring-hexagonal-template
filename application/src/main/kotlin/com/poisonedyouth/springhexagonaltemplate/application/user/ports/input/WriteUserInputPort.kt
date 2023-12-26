package com.poisonedyouth.springhexagonaltemplate.application.user.ports.input

import com.poisonedyouth.springhexagonaltemplate.application.user.ports.output.UserOutputPort
import com.poisonedyouth.springhexagonaltemplate.application.user.usecases.WriteUserUseCase
import com.poisonedyouth.springhexagonaltemplate.common.exception.NotFoundException
import com.poisonedyouth.springhexagonaltemplate.common.vo.Identity
import java.util.*

class WriteUserInputPort(
    private val userOutputPort: UserOutputPort,
) : WriteUserUseCase {
    override fun add(user: NewUserDto): UUID {
        val mappedUser = user.toUser()
        return userOutputPort.store(mappedUser).getId()
    }

    override fun update(user: UserDto) {
        val mappedUser = user.toUser()
        requireNotNull(userOutputPort.findBy(mappedUser.identity)) {
            "User with id '${user.identity} does not exist."
        }
        userOutputPort.store(mappedUser)
    }

    override fun delete(userId: UUID) {
        val userIdentity = Identity.UUIDIdentity(userId)
        val user =
            userOutputPort.findBy(userIdentity)
                ?: throw NotFoundException("User with id '${userIdentity} does not exist.")
        userOutputPort.delete(userIdentity)
    }
}
