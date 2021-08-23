buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.0-alpha09")
        classpath(kotlin("gradle-plugin", version = "1.5.10"))
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.18.0")
    }
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
        this.jvmTarget = "11"
    }
}
