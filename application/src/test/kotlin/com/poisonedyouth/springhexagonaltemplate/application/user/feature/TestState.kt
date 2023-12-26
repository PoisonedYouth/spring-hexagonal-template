package com.poisonedyouth.springhexagonaltemplate.application.user.feature

import arrow.core.Either

class TestState<T : Any, U : Any> {
    lateinit var input: T
    lateinit var output: Either<Throwable, U>
}
