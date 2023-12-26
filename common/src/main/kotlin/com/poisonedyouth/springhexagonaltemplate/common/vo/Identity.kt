package com.poisonedyouth.springhexagonaltemplate.common.vo

import java.util.*

sealed interface Identity {
    fun getId(): UUID

    data object NoIdentity : Identity {
        override fun getId(): UUID {
            error("Id not set yet.")
        }
    }

    @JvmInline
    value class UUIDIdentity(val value: UUID) : Identity {

        override fun getId(): UUID {
            return value
        }

        companion object {
            val NEW
                get() = UUIDIdentity(UUID.randomUUID())
        }
    }
}

fun UUID.toIdentity() = Identity.UUIDIdentity(this)
