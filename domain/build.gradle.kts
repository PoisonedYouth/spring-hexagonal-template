plugins{
    id("pitest-configuration")
}

dependencies {
    implementation(project(":common"))

    testImplementation(libs.junitJupiterApi)
    testImplementation(libs.junitJupiterEngine)
    testImplementation(libs.assertJ)
}