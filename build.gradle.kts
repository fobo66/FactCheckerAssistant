buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.0-alpha01")
        classpath(kotlin("gradle-plugin", version = "1.4.21-2"))
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.36")
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt").version("1.14.1")
}

allprojects {
    repositories {
        google()
        jcenter()
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