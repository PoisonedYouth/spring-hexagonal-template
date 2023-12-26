package com.poisonedyouth.springhexagonaltemplate.domain.user.vo

private const val MINIMUM_LENGTH = 3
private const val MAXIMUM_FIRST_NAME_LENGTH = 15
private const val MAXIMUM_LAST_NAME_LENGTH = 30

data class Name(val firstName: String, val lastName: String) {
    init {
        require(firstName.length in MINIMUM_LENGTH..MAXIMUM_FIRST_NAME_LENGTH) {
            "Firstname must be between $MINIMUM_LENGTH and $MAXIMUM_FIRST_NAME_LENGTH characters long."
        }

        require(lastName.length in MINIMUM_LENGTH..MAXIMUM_LAST_NAME_LENGTH) {
            "Lastname must be between $MINIMUM_LENGTH and $MAXIMUM_LAST_NAME_LENGTH characters long."
        }
    }
}
