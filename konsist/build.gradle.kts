dependencies{
    testRuntimeOnly(project(":domain"))
    testRuntimeOnly(project(":application"))
    testRuntimeOnly(project(":framework"))
    testImplementation(libs.konsist)
    testImplementation(libs.junitJupiterEngine)
    testImplementation(libs.junitJupiterApi)
}