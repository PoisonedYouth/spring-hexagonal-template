package com.poisonedyouth.springhexagonaltemplate.framework.adapters.input.rest

import com.poisonedyouth.springhexagonaltemplate.common.exception.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class RestAdapterControllerAdvice {

    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgumentException(exception: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.message)
    }

    @ExceptionHandler(NotFoundException::class)
    fun notFoundException(exception: NotFoundException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.message)
    }
}
