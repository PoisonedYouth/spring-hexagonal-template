package com.poisonedyouth.springhexagonaltemplate.framework.adapters.input.rest.security

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class DefaultUserDetailsService : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        // Load user from database
        val existingUser = findUser(username)

        existingUser?.let {
            return User(it.username, it.password, emptyList())
        } ?: throw UsernameNotFoundException("User with username '${username}' not found.")
    }

    private data class UserCredentials(
        val username: String,
        val password: String,
    )

    private fun findUser(username: String): UserCredentials? {
        return UserCredentials(username = username, password = "0123456789")
    }
}
