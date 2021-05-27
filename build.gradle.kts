buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0-alpha13")
        classpath(kotlin("gradle-plugin", version = "1.4.32"))
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.36")
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt").version("1.16.0")
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks {
    withType<io.gitlab.arturbosch.detekt.Detekt> {
        // Target version of the generated JVM bytecode. It is used for type resolution.
        this.jvmTarget = "1.8"
    }
}