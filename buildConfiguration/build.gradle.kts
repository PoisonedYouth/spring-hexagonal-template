plugins {
    `kotlin-dsl`
}

repositories{
    gradlePluginPortal()
    mavenCentral()
}

dependencies{
    compileOnly(libs.kotlinJvmLib)

    implementation(libs.pitestJunit5)
    implementation(libs.pitestPluginLib)

    compileOnly(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}