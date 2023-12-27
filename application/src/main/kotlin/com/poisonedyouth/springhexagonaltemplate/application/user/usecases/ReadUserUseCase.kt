package com.poisonedyouth.springhexagonaltemplate.application.user.usecases

import com.poisonedyouth.springhexagonaltemplate.common.vo.Identity
import com.poisonedyouth.springhexagonaltemplate.domain.user.entity.User

interface ReadUserUseCase {
    fun find(userId: Identity): User?

    fun all(): List<User>
}
