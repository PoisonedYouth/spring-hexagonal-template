package com.poisonedyouth.springhexagonaltemplate.application.user.ports.output

import com.poisonedyouth.springhexagonaltemplate.common.vo.Identity
import com.poisonedyouth.springhexagonaltemplate.domain.user.entity.User

interface UserOutputPort {

    fun store(user: User): Identity

    fun findBy(identity: Identity): User?

    fun delete(identity: Identity)

    fun all(): List<User>
}
