package com.poisonedyouth.springhexagonaltemplate.application.user.feature.user

import arrow.core.Either
import com.poisonedyouth.springhexagonaltemplate.application.user.feature.TestState
import com.poisonedyouth.springhexagonaltemplate.application.user.feature.TestUserOutputPort
import com.poisonedyouth.springhexagonaltemplate.application.user.ports.input.WriteUserInputPort
import com.poisonedyouth.springhexagonaltemplate.common.vo.Identity
import com.poisonedyouth.springhexagonaltemplate.domain.user.entity.User
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.Address
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.Country
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.Name
import com.poisonedyouth.springhexagonaltemplate.domain.user.vo.ZipCode
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize

class FindUserSteps {

    private val userOutputPort = TestUserOutputPort()
    private val writeUserInputPort = WriteUserInputPort(userOutputPort)

    private val testState = TestState<MutableList<Identity>, List<User>>()

    @Given("the following users exist:")
    fun `create valid user object`(dataTable: DataTable) {
        testState.input = mutableListOf()
        dataTable.asLists().forEach { user ->
            val user =
                User(
                    identity = Identity.NoIdentity,
                    name = Name(firstName = user[0], lastName = user[1]),
                    address =
                        Address(
                            streetName = user[2],
                            streetNumber = user[3],
                            city = user[4],
                            zipCode = ZipCode(user[5].toInt()),
                            country = Country(name = user[6], code = user[7])
                        )
                )
            testState.input.add(writeUserInputPort.add(user))
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
