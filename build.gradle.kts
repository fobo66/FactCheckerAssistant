buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.0-alpha09")
        classpath(kotlin("gradle-plugin", version = "1.7.20"))
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.44.2")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.22.0")
    }
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
