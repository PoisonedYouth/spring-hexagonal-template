plugins{
    id("pitest-configuration")
}

dependencies {
    testImplementation(libs.junitJupiterApi)
    testImplementation(libs.junitJupiterEngine)
    testImplementation(libs.assertJ)
}