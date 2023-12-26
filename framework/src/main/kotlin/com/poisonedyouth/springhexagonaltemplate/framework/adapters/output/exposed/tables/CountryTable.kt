package com.poisonedyouth.springhexagonaltemplate.framework.adapters.output.exposed.tables

import org.jetbrains.exposed.dao.id.UUIDTable

private const val MAXIMUM_NAME_LENGTH = 100
private const val MAXIMUM_CODE_LENGTH = 10

object CountryTable : UUIDTable("country") {
    val name = varchar("full_name", MAXIMUM_NAME_LENGTH)
    val code = varchar("code", MAXIMUM_CODE_LENGTH).uniqueIndex("country_code")
}
