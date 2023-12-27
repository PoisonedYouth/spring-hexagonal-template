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
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.matchers.shouldNotBe

class AddUserSteps {

    private val userOutputPort = TestUserOutputPort()
    private val writeUserInputPort = WriteUserInputPort(userOutputPort)

    private val testState = TestState<User, Identity>()

    @Given("I have valid user input data available")
    fun `create valid user object`() {
        testState.input =
            User(
                identity = Identity.NoIdentity,
                name = Name(firstName = "John", lastName = "Doe"),
                address =
                    Address(
                        streetName = "Main Street",
                        streetNumber = "12A",
                        city = "Los Angeles",
                        zipCode = ZipCode(12345),
                        country = Country("United States", "USA")
                    )
            )
    }

    @When("I add the user to the system")
    fun `add user object to system`() {
        testState.output = Either.catch { writeUserInputPort.add(testState.input) }
    }

    @Then("the id of the user is returned")
    fun `check for the identity returned`() {
        testState.output.shouldBeRight().also { userOutputPort.findBy(it) shouldNotBe null }
    }
}
