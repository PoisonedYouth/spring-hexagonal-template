package com.poisonedyouth.springhexagonaltemplate.application.user.ports.input

import com.poisonedyouth.springhexagonaltemplate.application.user.ports.output.UserOutputPort
import com.poisonedyouth.springhexagonaltemplate.common.vo.Identity
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class WriteUserInputPortTest {

    private val userOutputPort: UserOutputPort = mock()
    private val addUserInputPort = WriteUserInputPort(userOutputPort)

    @Test
    fun `should throw an exception when user is not valid`() {
        // given
        val user =
            NewUserDto(
                firstName = "John",
                lastName = "Doe",
                address =
                    AddressDto(
                        streetName = "test",
                        streetNumber = "test",
                        city = "test",
                        zipCode = 123456, // Invalid
                        country = CountryDto("United States", "USA")
                    )
            )

        // when/then
        assertThatThrownBy { addUserInputPort.add(user) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("Zip code must be between 10000 and 99999")
    }

    @Test
    fun `should store user and return identity when user is valid`() {
        // given
        val identity = Identity.UUIDIdentity.NEW
        whenever(userOutputPort.store(any())).thenReturn(identity)

        val user =
            NewUserDto(
                firstName = "John",
                lastName = "Doe",
                address =
                    AddressDto(
                        streetName = "test",
                        streetNumber = "test",
                        city = "test",
                        zipCode = 12345,
                        country = CountryDto("United States", "USA")
                    )
            )

        // when
        val actual = addUserInputPort.add(user)

        // then
        assertThat(actual).isEqualTo(identity.value)
    }
}
