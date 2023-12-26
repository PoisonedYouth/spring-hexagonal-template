plugins{
    id("pitest-configuration")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":common"))

    testImplementation(platform(libs.arrowStack))
    testImplementation(libs.arrowCore)
    testImplementation(libs.kotlinMockito)
    testImplementation(libs.junitJupiterEngine)
    testImplementation(libs.junitJupiterApi)
    testImplementation(libs.assertJ)
    testImplementation(libs.kotest)
    testImplementation(libs.kotestArrowAssertions)


    testImplementation(platform(libs.cucumberCore))
    testImplementation(libs.cucumberJava)
    testImplementation(libs.cucumberPlatformEngine)
    testImplementation(libs.junitPlatformSuite)
}

tasks.withType<Test> {
    useJUnitPlatform()
    systemProperty("cucumber.junit-platform.naming-strategy", "long")
}