package com.poisonedyouth.springhexagonaltemplate.application.user.ports.input

import com.poisonedyouth.springhexagonaltemplate.common.vo.Identity
import com.poisonedyouth.springhexagonaltemplate.domain.user.entity.User
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.Address
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.Country
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.Name
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.ZipCode
import java.util.*

data class NewUserDto(val firstName: String, val lastName: String, val address: AddressDto)

data class UserDto(
    val identity: UUID,
    val firstName: String,
    val lastName: String,
    val address: AddressDto
)

fun NewUserDto.toUser() =
    User(
        identity = Identity.NoIdentity,
        name = Name(firstName = this.firstName, lastName = this.lastName),
        address = this.address.toAddress()
    )

fun UserDto.toUser() =
    User(
        identity = Identity.UUIDIdentity(this.identity),
        name = Name(firstName = this.firstName, lastName = this.lastName),
        address = this.address.toAddress()
    )

fun User.toUserDto() =
    UserDto(
        identity = this.identity.getId(),
        firstName = this.name.firstName,
        lastName = this.name.lastName,
        address = this.address.toAddressDto()
    )

data class AddressDto(
    val streetName: String,
    val streetNumber: String,
    val city: String,
    val zipCode: Int,
    val country: CountryDto
)

fun AddressDto.toAddress() =
    Address(
        streetName = this.streetName,
        streetNumber = this.streetNumber,
        city = this.city,
        zipCode = ZipCode(this.zipCode),
        country = this.country.toCountry()
    )

fun Address.toAddressDto() =
    AddressDto(
        streetName = this.streetName,
        streetNumber = this.streetNumber,
        city = this.city,
        zipCode = this.zipCode.value,
        country = this.country.toCountryDto()
    )

data class CountryDto(val name: String, val code: String)

fun CountryDto.toCountry() = Country(name = this.name, code = this.code)

fun Country.toCountryDto() = CountryDto(name = this.name, code = this.code)
