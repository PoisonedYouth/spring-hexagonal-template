package com.poisonedyouth.springhexagonaltemplate.framework.adapters.output.exposed.tables

import org.jetbrains.exposed.dao.id.UUIDTable

private const val MAXIMUM_NAME_LENGTH = 100

object UserTable : UUIDTable("app_user") {
    val firstname = varchar("first_name", MAXIMUM_NAME_LENGTH)
    val lastname = varchar("last_name", MAXIMUM_NAME_LENGTH)
    val address = reference("address_id", AddressTable)
}
