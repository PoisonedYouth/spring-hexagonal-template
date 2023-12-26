package com.poisonedyouth.springhexagonaltemplate.framework.adapters.output

import org.testcontainers.containers.PostgreSQLContainer

internal class KPostgreSQLContainer(image: String) :
    PostgreSQLContainer<KPostgreSQLContainer>(image)
