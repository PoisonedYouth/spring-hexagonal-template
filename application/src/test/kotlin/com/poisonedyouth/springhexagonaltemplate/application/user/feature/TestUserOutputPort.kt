package com.poisonedyouth.springhexagonaltemplate.application.user.feature

import com.poisonedyouth.springhexagonaltemplate.application.user.ports.output.UserOutputPort
import com.poisonedyouth.springhexagonaltemplate.common.vo.Identity
import com.poisonedyouth.springhexagonaltemplate.domain.user.entity.User

class TestUserOutputPort : UserOutputPort {
    private val userList = mutableListOf<User>()

    override fun store(user: User): Identity {
        val newUser = user.copy(identity = Identity.UUIDIdentity.NEW)
        userList.add(newUser)
        return newUser.identity
    }

    override fun findBy(identity: Identity): User? {
        return userList.firstOrNull { it.identity == identity }
    }

    override fun delete(identity: Identity) {
        userList.removeIf { it.identity == identity }
    }

    override fun all(): List<User> {
        return userList.toList()
    }
}
