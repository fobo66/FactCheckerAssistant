import com.android.sdklib.AndroidVersion.VersionCodes
import dev.detekt.gradle.Detekt
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.test)
    kotlin("android")
    alias(libs.plugins.baseline.profile)
    alias(libs.plugins.kotlinter)
}

android {
    namespace = "dev.fobo66.baselineprofile"
    compileSdk = VersionCodes.BAKLAVA

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    defaultConfig {
        minSdk = VersionCodes.R
        targetSdk = VersionCodes.BAKLAVA

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    targetProjectPath = ":app"
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

tasks.withType<Detekt> {
    jvmTarget = "17"
}

dependencies {
    implementation(libs.androidx.test.junit)
    implementation(libs.espresso.core)
    implementation(libs.androidx.test.uiautomator)
    implementation(libs.androidx.benchmark.macro)
}
