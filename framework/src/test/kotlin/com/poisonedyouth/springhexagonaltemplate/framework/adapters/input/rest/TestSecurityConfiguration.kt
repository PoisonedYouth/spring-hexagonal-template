package com.poisonedyouth.springhexagonaltemplate.framework.adapters.input.rest

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.test.context.ActiveProfiles

@TestConfiguration
@ActiveProfiles("test")
class TestSecurityConfiguration {

    @Bean
    @Profile("test")
    fun passwordEncoder(): PasswordEncoder {
        return object : PasswordEncoder {
            override fun encode(rawPassword: CharSequence): String {
                return rawPassword.toString()
            }

            override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean {
                return rawPassword.toString() == encodedPassword
            }
        }
    }

    @Bean
    @Profile("test")
    fun userDetailsService(passwordEncoder: PasswordEncoder): UserDetailsService {
        val user: UserDetails =
            User.withUsername("test").password(passwordEncoder.encode("test")).roles("USER").build()
        return InMemoryUserDetailsManager(user)
    }
}
