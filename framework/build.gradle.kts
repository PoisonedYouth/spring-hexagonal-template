plugins {
    alias(libs.plugins.springDependencyManagement)
    alias(libs.plugins.kotlinSpring)
    alias(libs.plugins.flyway)
    id("pitest-configuration")
}
dependencies {
    implementation(project(":domain"))
    implementation(project(":common"))
    implementation(project(":application"))

    // Spring
    implementation(libs.bundles.springbootLibraries)
    implementation(libs.kotlinReflect)
    implementation(libs.jacksonKotlin)

    // Exposed
    implementation(libs.exposedStarter)
    implementation(libs.exposedJavaTime)

    //Persistence
    runtimeOnly(libs.postgresqlConnector)
    implementation(libs.hikari)

    // Flyway
    implementation(libs.flyway)

    // OpenAPI
    implementation(libs.springdocOpenApi)

    // Testing
    testImplementation(libs.springBootStarterTest)
    testImplementation(libs.springBootStarterSecurityTest)
    testImplementation(libs.kotlinMockito)
    testRuntimeOnly(libs.flywayPostgresql)

    testImplementation(libs.testContainersCore)
    testImplementation(libs.testContainersPostgresql)
    testImplementation(libs.testContainersJunitJupiter)
}