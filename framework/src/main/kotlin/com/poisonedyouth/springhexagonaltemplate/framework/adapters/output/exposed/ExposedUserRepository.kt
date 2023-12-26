package com.poisonedyouth.springhexagonaltemplate.framework.adapters.output.exposed

import com.poisonedyouth.springhexagonaltemplate.application.user.ports.output.UserOutputPort
import com.poisonedyouth.springhexagonaltemplate.common.vo.Identity
import com.poisonedyouth.springhexagonaltemplate.domain.user.entity.User
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.Address
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.Country
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.Name
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.ZipCode
import com.poisonedyouth.springhexagonaltemplate.framework.adapters.output.exposed.tables.AddressTable
import com.poisonedyouth.springhexagonaltemplate.framework.adapters.output.exposed.tables.CountryTable
import com.poisonedyouth.springhexagonaltemplate.framework.adapters.output.exposed.tables.UserTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class ExposedUserRepository : UserOutputPort {
    override fun store(user: User): Identity = transaction {
        val countryRow =
            CountryTable.select { CountryTable.code eq user.address.country.code }.singleOrNull()
        val countryId =
            if (countryRow == null) {
                CountryTable.insertAndGetId {
                    it[name] = user.address.country.name
                    it[code] = user.address.country.code
                }
            } else {
                countryRow[CountryTable.id]
            }

        val addressId =
            AddressTable.insertAndGetId {
                it[city] = user.address.city
                it[streetName] = user.address.streetName
                it[streetNumber] = user.address.streetNumber
                it[zipCode] = user.address.zipCode.value
                it[country] = countryId
            }

        val id =
            when (user.identity) {
                Identity.NoIdentity -> {
                    UserTable.insertAndGetId {
                            it[firstname] = user.name.firstName
                            it[lastname] = user.name.lastName
                            it[address] = addressId
                        }
                        .value
                }
                is Identity.UUIDIdentity -> {
                    UserTable.update({
                        UserTable.id eq (user.identity as Identity.UUIDIdentity).value
                    }) {
                        it[firstname] = user.name.firstName
                        it[lastname] = user.name.lastName
                        it[address] = addressId
                    }
                    (user.identity as Identity.UUIDIdentity).value
                }
            }
        Identity.UUIDIdentity(id)
    }

    override fun findBy(identity: Identity): User? = transaction {
        if (identity is Identity.NoIdentity) {
            null
        } else {
            UserTable.select { UserTable.id eq identity.getId() }.singleOrNull()?.toUser()
        }
    }

    override fun delete(identity: Identity) = transaction {
        UserTable.deleteWhere { UserTable.id eq identity.getId() }
        Unit
    }

    override fun all(): List<User> = transaction { UserTable.selectAll().map { it.toUser() } }

    private fun ResultRow.toUser(): User {
        val addressRow =
            AddressTable.select { AddressTable.id eq this@toUser[UserTable.address] }.single()
        val countryRow =
            CountryTable.select { CountryTable.id eq addressRow[AddressTable.country] }.single()
        return User(
            identity = Identity.UUIDIdentity(this@toUser[UserTable.id].value),
            name = Name(this@toUser[UserTable.firstname], this@toUser[UserTable.lastname]),
            address =
                Address(
                    city = addressRow[AddressTable.city],
                    streetName = addressRow[AddressTable.streetName],
                    streetNumber = addressRow[AddressTable.streetNumber],
                    zipCode = ZipCode(addressRow[AddressTable.zipCode]),
                    country = Country(countryRow[CountryTable.name], countryRow[CountryTable.code])
                )
        )
    }
}
