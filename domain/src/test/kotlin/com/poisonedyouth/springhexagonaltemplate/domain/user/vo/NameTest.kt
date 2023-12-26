package com.poisonedyouth.springhexagonaltemplate.domain.user.vo

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

internal class NameTest {

    @Test
    fun `Name is correctly created when valid first and last names are given`() {
        // Given
        val firstName = "John"
        val lastName = "Doe"

        // When
        val name = Name(firstName, lastName)

        // Then
        assertThat(name.firstName).isEqualTo(firstName)
        assertThat(name.lastName).isEqualTo(lastName)
    }

    @Test
    fun `Exception is thrown when first name is too short`() {
        // Given
        val firstName = "Jo"
        val lastName = "Doe"

        // When & Then
        assertThatThrownBy { Name(firstName, lastName) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Firstname must be between 3 and 15 characters long.")
    }

    @Test
    fun `Exception is thrown when last name is too short`() {
        // Given
        val firstName = "John"
        val lastName = "Do"

        // When & Then
        assertThatThrownBy { Name(firstName, lastName) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Lastname must be between 3 and 30 characters long.")
    }

    @Test
    fun `Exception is thrown when first name is too long`() {
        // Given
        val firstName = "JohnJacobJingleheimerSchmidt"
        val lastName = "Doe"

        // When & Then
        assertThatThrownBy { Name(firstName, lastName) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Firstname must be between 3 and 15 characters long.")
    }

    @Test
    fun `Exception is thrown when last name is too long`() {
        // Given
        val firstName = "John"
        val lastName = "DoeDoeDoeDoeDoeDoeDoeDoeDoeDoeDoe"

        // When & Then
        assertThatThrownBy { Name(firstName, lastName) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Lastname must be between 3 and 30 characters long.")
    }
}
