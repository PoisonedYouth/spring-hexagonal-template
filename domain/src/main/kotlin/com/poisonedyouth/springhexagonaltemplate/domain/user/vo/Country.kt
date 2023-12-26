package com.poisonedyouth.springhexagonaltemplate.domain.user.vo

data class Country(val name: String, val code: String) {
    init {
        require(code.matches(Regex("^[A-Z]{1,3}$"))) { "Code must be compatible with ISO 3166" }
    }
}
