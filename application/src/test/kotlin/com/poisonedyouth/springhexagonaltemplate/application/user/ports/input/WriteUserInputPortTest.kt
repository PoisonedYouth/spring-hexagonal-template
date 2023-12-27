package com.poisonedyouth.springhexagonaltemplate.application.user.ports.input

import com.poisonedyouth.springhexagonaltemplate.application.user.ports.output.UserOutputPort
import com.poisonedyouth.springhexagonaltemplate.common.vo.Identity
import com.poisonedyouth.springhexagonaltemplate.domain.user.entity.User
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.Address
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.Country
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.Name
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.ZipCode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class WriteUserInputPortTest {

    private val userOutputPort: UserOutputPort = mock()
    private val addUserInputPort = WriteUserInputPort(userOutputPort)

    @Test
    fun `should store user and return identity when user is valid`() {
        // given
        val identity = Identity.UUIDIdentity.NEW
        whenever(userOutputPort.store(any())).thenReturn(identity)

        val user =
            User(
                identity = Identity.NoIdentity,
                name = Name(firstName = "John", lastName = "Doe"),
                address =
                    Address(
                        streetName = "test",
                        streetNumber = "test",
                        city = "test",
                        zipCode = ZipCode(12345),
                        country = Country("United States", "USA")
                    )
            )

        // when
        val actual = addUserInputPort.add(user)

        // then
        assertThat(actual).isEqualTo(identity)
    }
}
