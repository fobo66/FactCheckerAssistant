plugins {
    alias(androidx.plugins.application) apply false
    alias(androidx.plugins.library) apply false
    alias(androidx.plugins.test) apply false
    kotlin("android") version libs.versions.kotlin apply false
    alias(libs.plugins.ksp) apply false
    alias(di.plugins.hilt) apply false
    alias(analysis.plugins.detekt) apply false
    alias(testing.plugins.junit) apply false
    alias(androidx.plugins.baseline.profile) apply false
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.layout.buildDirectory)
    }

    withType<io.gitlab.arturbosch.detekt.Detekt> {
        // Target version of the generated JVM bytecode. It is used for type resolution.
        this.jvmTarget = "17"
    }
}
