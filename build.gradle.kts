buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.0-alpha08")
        classpath(kotlin("gradle-plugin", version = "1.7.0"))
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.43")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.21.0")
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
