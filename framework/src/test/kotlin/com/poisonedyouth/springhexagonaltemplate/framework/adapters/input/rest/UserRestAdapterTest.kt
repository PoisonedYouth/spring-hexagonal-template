package com.poisonedyouth.springhexagonaltemplate.framework.adapters.input.rest

// Kotlin imports
import com.fasterxml.jackson.databind.ObjectMapper
import com.poisonedyouth.springhexagonaltemplate.application.user.usecases.ReadUserUseCase
import com.poisonedyouth.springhexagonaltemplate.application.user.usecases.WriteUserUseCase
import com.poisonedyouth.springhexagonaltemplate.common.exception.NotFoundException
import com.poisonedyouth.springhexagonaltemplate.common.vo.Identity
import com.poisonedyouth.springhexagonaltemplate.common.vo.toIdentity
import com.poisonedyouth.springhexagonaltemplate.domain.user.entity.User
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.Address
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.Country
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.Name
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.ZipCode
import com.poisonedyouth.springhexagonaltemplate.framework.adapters.input.rest.security.SecurityConfiguration
import com.poisonedyouth.springhexagonaltemplate.framework.configuration.ApplicationConfiguration
import java.util.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(UserRestAdapter::class)
@Import(ApplicationConfiguration::class, SecurityConfiguration::class)
class UserRestAdapterTest {
    @Autowired private lateinit var mockMvc: MockMvc

    @MockBean private lateinit var writeUserUseCase: WriteUserUseCase

    @MockBean private lateinit var readUserUseCase: ReadUserUseCase

    @Autowired private lateinit var objectMapper: ObjectMapper

    @Test
    @WithMockUser(username = "test", password = "test")
    fun `given non-existing user id, when findUser is called, return not found status`() {
        // given
        val id = UUID.randomUUID().toIdentity()
        whenever(readUserUseCase.find(id)).thenReturn(null)

        // when
        val result =
            mockMvc.perform(
                get("/api/v1/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .queryParam("id", "${id.value}")
            )

        // then
        result.andExpect(status().isNotFound()).andExpect { res ->
            assertThat(res.response.contentAsString)
                .isEqualTo("User with id '${id.value}' does not exist.")
        }
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    fun `given existing user id, when findUser is called, then return user details`() {
        // given
        val id = UUID.randomUUID().toIdentity()
        val user =
            User(
                identity = id,
                name = Name(firstName = "John", lastName = "Doe"),
                address =
                    Address(
                        streetName = "Street",
                        streetNumber = "123",
                        city = "City",
                        zipCode = ZipCode(12346),
                        country = Country(name = "Country", code = "CR")
                    )
            )
        whenever(readUserUseCase.find(id)).thenReturn(user)

        // when
        val result =
            mockMvc.perform(
                get("/api/v1/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .queryParam("id", "${id.value}")
            )

        // then
        result.andExpect(status().isOk()).andExpect { res ->
            assertThat(objectMapper.readValue(res.response.contentAsString, UserDto::class.java))
                .isEqualTo(user.toUserDto())
        }
    }

    @Test
    fun `given existing user id, when findUser is called without credentials, then not authorized is returned`() {
        // given
        val id = UUID.randomUUID().toIdentity()

        // when
        val result =
            mockMvc.perform(
                get("/api/v1/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .queryParam("id", "${id.value}")
            )

        // then
        result.andExpect(status().isUnauthorized())
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    fun `given valid user data, when updateUser is called, then an existing user is updated`() {
        // given
        val identity = Identity.UUIDIdentity.NEW
        val user =
            UserDto(
                identity = identity.value,
                firstName = "John",
                lastName = "Doe",
                address =
                    AddressDto(
                        streetName = "Street",
                        streetNumber = "123",
                        city = "City",
                        zipCode = 12345,
                        country = CountryDto(name = "Country", code = "CR")
                    )
            )

        // when
        val result =
            mockMvc.perform(
                put("/api/v1/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(user))
            )

        // then
        result.andExpect(status().isOk)
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    fun `given invalid user data, when updateUser is called, then a bad request is returned`() {
        // given
        val identity = Identity.UUIDIdentity.NEW
        val user =
            User(
                identity = identity,
                name = Name(firstName = "John", lastName = "Doe"),
                address =
                    Address(
                        streetName = "Street",
                        streetNumber = "123",
                        city = "City",
                        zipCode = ZipCode(12345),
                        country = Country(name = "Country", code = "CR")
                    )
            )

        whenever(writeUserUseCase.update(user)).thenThrow(IllegalArgumentException("Failed!"))

        // when
        val result =
            mockMvc.perform(
                put("/api/v1/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(user))
            )

        // then
        result.andExpect(status().isBadRequest)
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    fun `given existing userId, when deleteUser is called, then accepted is returned`() {
        // given
        val identity = Identity.UUIDIdentity.NEW

        // when
        val result =
            mockMvc.perform(
                delete("/api/v1/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .queryParam("id", "${identity.value}")
            )

        // then
        result.andExpect(status().isAccepted)
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    fun `given not existing userId, when deleteUser is called, then not found is returned`() {
        // given
        val identity = Identity.UUIDIdentity.NEW

        whenever(writeUserUseCase.delete(identity)).thenThrow(NotFoundException("Failed!"))

        // when
        val result =
            mockMvc.perform(
                delete("/api/v1/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .queryParam("id", "${identity.value}")
            )

        // then
        result.andExpect(status().isNotFound)
    }

    @Test
    fun `delete user when user not send authorization header, return not authorized`() {
        // given
        val identity = Identity.UUIDIdentity.NEW

        // when
        val result =
            mockMvc.perform(
                delete("/api/v1/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .queryParam("id", "${identity.value}")
            )

        // then
        result.andExpect(status().isUnauthorized)
    }
}
