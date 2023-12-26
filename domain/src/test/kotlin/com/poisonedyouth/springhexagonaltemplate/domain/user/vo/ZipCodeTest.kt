package com.poisonedyouth.springhexagonaltemplate.domain.user.vo

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class ZipCodeTest {

    @Test
    fun `ZipCode is correctly created when valid code is given`() {
        // Given
        val code = 12345

        // When
        val zipCode = ZipCode(code)

        // Then
        assertEquals(code, zipCode.value)
    }

    @Test
    fun `Exception is thrown when zip code is too small`() {
        // Given
        val code = 9999

        // When
        val exception = assertThrows<IllegalArgumentException> { ZipCode(code) }

        // Then
        assertEquals("Zip code must be between 10000 and 99999", exception.message)
    }

    @Test
    fun `Exception is thrown when zip code is too large`() {
        // Given
        val code = 100000

        // When
        val exception = assertThrows<IllegalArgumentException> { ZipCode(code) }

        // Then
        assertEquals("Zip code must be between 10000 and 99999", exception.message)
    }
}
