package com.poisonedyouth.springhexagonaltemplate.framework.adapters.output.exposed

import com.poisonedyouth.springhexagonaltemplate.common.vo.Identity
import com.poisonedyouth.springhexagonaltemplate.domain.user.entity.User
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.Address
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.Country
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.Name
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.ZipCode
import com.poisonedyouth.springhexagonaltemplate.framework.adapters.output.ClearDatabase
import com.poisonedyouth.springhexagonaltemplate.framework.adapters.output.EnableTestcontainers
import com.poisonedyouth.springhexagonaltemplate.framework.configuration.ApplicationConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest
@Import(
    ApplicationConfiguration::class,
    ExposedConfig::class,
)
@EnableTestcontainers
@ClearDatabase
internal class ExposedUserRepositoryTest {

    @Autowired private lateinit var userRepository: ExposedUserRepository

    @Test
    fun `storeUser should store user and return correct identity`() {
        // given
        val user =
            User(
                identity = Identity.NoIdentity,
                name = Name("John", "Doe"),
                address =
                    Address(
                        streetName = "Street Name",
                        streetNumber = "Street Number",
                        city = "City",
                        zipCode = ZipCode(12345),
                        country = Country(name = "United States", code = "USA")
                    )
            )
        // when
        val actual = userRepository.store(user)
        // then
        assertThat(actual).isInstanceOf(Identity.UUIDIdentity::class.java)
    }

    @Test
    fun `findUserBy should return null when no user is found`() {
        // given
        val identity = Identity.UUIDIdentity.NEW
        // when
        val actual = userRepository.findBy(identity)
        // then
        assertThat(actual).isNull()
    }

    @Test
    fun `findUserBy should return correct user when user is found`() {
        // given
        val user =
            User(
                identity = Identity.NoIdentity,
                name = Name("John", "Doe"),
                address =
                    Address(
                        streetName = "Street Name",
                        streetNumber = "Street Number",
                        city = "City",
                        zipCode = ZipCode(12345),
                        country = Country("United States", "USA")
                    )
            )

        val userId = userRepository.store(user)

        // when
        val actual = userRepository.findBy(userId)
        // then
        assertThat(actual).isNotNull()
        assertThat(actual).isEqualTo(user.copy(identity = userId))
    }
}
