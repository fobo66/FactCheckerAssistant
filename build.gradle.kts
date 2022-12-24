plugins {
    id("com.android.application") version androidx.versions.plugin apply false
    id("com.android.library") version androidx.versions.plugin apply false
    kotlin("android") version libs.versions.kotlin apply false
    kotlin("kapt") version libs.versions.kotlin apply false
    id("com.google.dagger.hilt.android") version di.versions.hilt apply false
    id("io.gitlab.arturbosch.detekt") version analysis.versions.detekt apply false
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }

    withType<io.gitlab.arturbosch.detekt.Detekt> {
        // Target version of the generated JVM bytecode. It is used for type resolution.
        this.jvmTarget = "11"
    }
}
