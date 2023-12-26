package com.poisonedyouth.springhexagonaltemplate.common.vo

import java.util.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

internal class IdentityTest {

    @Test
    fun `NoIdentity getId() call must throw an exception`() {
        // given
        val noIdentity = Identity.NoIdentity

        // when / then
        assertThatThrownBy { noIdentity.getId() }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessage("Id not set yet.")
    }

    @Test
    fun `UUIDIdentity getId() call must return initialized UUID`() {
        // given
        val uuid = UUID.randomUUID()
        val uuidIdentity = Identity.UUIDIdentity(uuid)

        // when
        val resultId = uuidIdentity.getId()

        // then
        assertThat(resultId).isEqualTo(uuid)
    }

    @Test
    fun `UUIDIdentity NEW property must return new UUIDIdentity instance`() {
        // given / when
        val uuidIdentity = Identity.UUIDIdentity.NEW

        // then
        assertThat(uuidIdentity).isInstanceOf(Identity.UUIDIdentity::class.java)
    }
}
