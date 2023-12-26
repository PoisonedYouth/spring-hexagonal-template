package com.poisonedyouth.springhexagonaltemplate.application.user.feature.user

import arrow.core.Either
import com.poisonedyouth.springhexagonaltemplate.application.user.feature.TestState
import com.poisonedyouth.springhexagonaltemplate.application.user.feature.TestUserOutputPort
import com.poisonedyouth.springhexagonaltemplate.application.user.ports.input.AddressDto
import com.poisonedyouth.springhexagonaltemplate.application.user.ports.input.CountryDto
import com.poisonedyouth.springhexagonaltemplate.application.user.ports.input.NewUserDto
import com.poisonedyouth.springhexagonaltemplate.application.user.ports.input.WriteUserInputPort
import com.poisonedyouth.springhexagonaltemplate.domain.user.entity.User
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import java.util.*

class FindUserSteps {

    private val userOutputPort = TestUserOutputPort()
    private val writeUserInputPort = WriteUserInputPort(userOutputPort)

    private val testState = TestState<MutableList<UUID>, List<User>>()

    @Given("the following users exist:")
    fun `create valid user object`(dataTable: DataTable) {
        testState.input = mutableListOf()
        dataTable.asLists().forEach { user ->
            val newUserDto =
                NewUserDto(
                    firstName = user[0],
                    lastName = user[1],
                    address =
                        AddressDto(
                            streetName = user[2],
                            streetNumber = user[3],
                            city = user[4],
                            zipCode = user[5].toInt(),
                            country = CountryDto(name = user[6], code = user[7])
                        )
                )
            testState.input.add(writeUserInputPort.add(newUserDto))
        }
    }

    @When("I request for all user")
    fun `request for all user`() {
        testState.output = Either.catch { userOutputPort.all() }
    }

    @Then("there are {int} users returned")
    fun `check that exactly the expected user are returned`(value: Int) {
        testState.output
            .shouldBeRight()
            .shouldHaveSize(value)
            .map { it.identity }
            .shouldContainExactly(testState.input)
    }
}
