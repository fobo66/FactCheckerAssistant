plugins {
    alias(libs.plugins.android.app) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.junit) apply false
    alias(libs.plugins.baseline.profile) apply false
    alias(libs.plugins.kotlinter)
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.layout.buildDirectory)
    }
}

subprojects {
    tasks {
        withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
            // Target version of the generated JVM bytecode. It is used for type resolution.
            jvmTarget = "17"
        }
        withType<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>().configureEach {
            // Target version of the generated JVM bytecode. It is used for type resolution.
            jvmTarget = "17"
        }
    }
}
