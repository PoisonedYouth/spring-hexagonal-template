package com.poisonedyouth.springhexagonaltemplate.domain.user.vo

private const val MINIMUM_ZIP_CODE = 10000
private const val MAXIMUM_ZIP_CODE = 99999

@JvmInline
value class ZipCode(val value: Int) {
    init {
        require(value in MINIMUM_ZIP_CODE..MAXIMUM_ZIP_CODE) {
            "Zip code must be between $MINIMUM_ZIP_CODE and $MAXIMUM_ZIP_CODE"
        }
    }
}
