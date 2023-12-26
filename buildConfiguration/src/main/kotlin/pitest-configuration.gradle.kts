import info.solidsoft.gradle.pitest.PitestPluginExtension
import info.solidsoft.gradle.pitest.PitestTask

plugins {
    id("info.solidsoft.pitest")
}

val pitestJunit5Version = libs.pitestJunit5.get().version
val pitestLibVersion = libs.pitestPluginLib.get().version

tasks.withType<PitestTask> {
    testPlugin = "junit5"
    mutationThreshold = 10
    outputFormats = listOf("HTML")
    threads = 8
    failWhenNoMutations = false
    jvmArgs = listOf("-Xmx16G")
    avoidCallsTo = listOf("kotlin.jvm.internal")
    failWhenNoMutations = false
    targetClasses = listOf("com.poisonedyouth.spring-hexagonal-template.*")
}

extensions.configure(PitestPluginExtension::class){
    junit5PluginVersion = pitestJunit5Version
    pitestVersion = pitestLibVersion
    useClasspathFile = true
    useClasspathJar = true
}