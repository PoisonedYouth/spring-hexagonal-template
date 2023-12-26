package com.poisonedyouth.springhexagonaltemplate.framework.adapters.output.exposed.tables

import org.jetbrains.exposed.dao.id.UUIDTable

private const val MAXIMUM_STREET_NAME_LENGTH = 100
private const val MAXIMUM_STREET_NUMBER_LENGTH = 50
private const val MAXIMUM_CITY_LENGTH = 100

object AddressTable : UUIDTable("address") {
    val streetName = varchar("street_name", MAXIMUM_STREET_NAME_LENGTH)
    val streetNumber = varchar("street_number", MAXIMUM_STREET_NUMBER_LENGTH)
    val city = varchar("city", MAXIMUM_CITY_LENGTH)
    val zipCode = integer("zip_code")
    val country = reference("country_id", CountryTable)
}
