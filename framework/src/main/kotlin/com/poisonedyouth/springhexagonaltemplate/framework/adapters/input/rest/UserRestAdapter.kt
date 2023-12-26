package com.poisonedyouth.springhexagonaltemplate.framework.adapters.input.rest

import com.poisonedyouth.springhexagonaltemplate.application.user.ports.input.UserDto
import com.poisonedyouth.springhexagonaltemplate.application.user.usecases.ReadUserUseCase
import com.poisonedyouth.springhexagonaltemplate.application.user.usecases.WriteUserUseCase
import java.util.*
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/user")
class UserRestAdapter(
    private val writeUserUseCase: WriteUserUseCase,
    private val readUserUseCase: ReadUserUseCase,
) {
    @PutMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun updateUser(@RequestBody user: UserDto): ResponseEntity<*> {
        writeUserUseCase.update(user)
        return ResponseEntity.ok().build<Unit>()
    }

    @GetMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun findUser(@RequestParam id: UUID): ResponseEntity<*> {
        val existingUser = readUserUseCase.find(id)
        return if (existingUser == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id '$id' does not exist.")
        } else {
            ResponseEntity.ok(existingUser)
        }
    }

    @DeleteMapping
    fun deleteUser(@RequestParam id: UUID): ResponseEntity<*> {
        writeUserUseCase.delete(id)
        return ResponseEntity.accepted().build<Unit>()
    }

    @GetMapping(
        "/all",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun getAll(): ResponseEntity<*> {
        return ResponseEntity.ok(readUserUseCase.all())
    }
}
