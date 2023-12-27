package com.poisonedyouth.springhexagonaltemplate.framework.adapters.input.rest

import com.poisonedyouth.springhexagonaltemplate.application.user.usecases.WriteUserUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/authentication")
class AuthenticationRestAdapter(private val writeUserUseCase: WriteUserUseCase) {

    @PostMapping(
        "user",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun addUser(@RequestBody user: NewUserDto): ResponseEntity<*> {
        return ResponseEntity(writeUserUseCase.add(user.toUser()), HttpStatus.CREATED)
    }
}
