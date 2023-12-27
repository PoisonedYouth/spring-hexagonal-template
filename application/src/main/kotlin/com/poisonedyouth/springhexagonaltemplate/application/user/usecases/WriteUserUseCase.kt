package com.poisonedyouth.springhexagonaltemplate.application.user.usecases

import com.poisonedyouth.springhexagonaltemplate.common.vo.Identity
import com.poisonedyouth.springhexagonaltemplate.domain.user.entity.User

interface WriteUserUseCase {
    fun add(user: User): Identity

    fun update(user: User)

    fun delete(userId: Identity)
}
