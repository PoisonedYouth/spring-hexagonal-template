import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlinJvm) apply true
    alias(libs.plugins.ktfmtPlugin) apply true
    alias(libs.plugins.detektPlugin) apply true

    alias(libs.plugins.versionUpdate)
    alias(libs.plugins.catalogUpdate)
}

group = "com.poisonedyouth"
version = "0.0.1"

val kotlinJvmPluginId: String = libs.plugins.kotlinJvm.get().pluginId
val detektPluginId: String = libs.plugins.detektPlugin.get().pluginId
val ktfmtPluginId: String = libs.plugins.ktfmtPlugin.get().pluginId
val pitestPluginId: String = libs.plugins.pitestPlugin.get().pluginId
val pitestJunit5Version = libs.pitestJunit5.get().version
val pitestLibVersion = libs.pitestPluginLib.get().version
val javaTargetVersion: String = libs.versions.javaVersion.get()


allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin(kotlinJvmPluginId)
        plugin(detektPluginId)
        plugin(ktfmtPluginId)
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = javaTargetVersion
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    ktfmt {
        kotlinLangStyle()
    }

    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin") {
                useVersion(io.gitlab.arturbosch.detekt.getSupportedKotlinVersion())
            }
        }
    }
}
