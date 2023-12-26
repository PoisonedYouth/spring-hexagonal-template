package com.poisonedyouth.springhexagonaltemplate.domain.user.vo

data class Address(
    val streetName: String,
    val streetNumber: String,
    val city: String,
    val zipCode: ZipCode,
    val country: Country
)
