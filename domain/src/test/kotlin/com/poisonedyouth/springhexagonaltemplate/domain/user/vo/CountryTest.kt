package com.poisonedyouth.springhexagonaltemplate.domain.user.vo

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

internal class CountryTest {

    @Test
    fun `should create country`() {
        // When
        val country = Country("Test Name", "TN")

        // Then
        assertThat(country).isEqualTo(Country("Test Name", "TN"))
    }

    @Test
    fun `should throw exception for invalid code`() {
        // When
        val exception = assertThatThrownBy { Country("Test Name", "INVALID") }

        // Then
        exception
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Code must be compatible with ISO 3166")
    }

    @Test
    fun `should throw exception for lowercase code`() {
        // When
        val exception = assertThatThrownBy { Country("Test Name", "tn") }

        // Then
        exception
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Code must be compatible with ISO 3166")
    }
}
