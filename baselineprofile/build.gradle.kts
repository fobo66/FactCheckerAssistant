import com.android.sdklib.AndroidVersion.VersionCodes

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

    kotlinOptions {
        jvmTarget = "17"
    }

    defaultConfig {
        minSdk = VersionCodes.P
        targetSdk = VersionCodes.BAKLAVA

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    targetProjectPath = ":app"
}

dependencies {
    implementation(libs.androidx.test.junit)
    implementation(libs.espresso.core)
    implementation(libs.androidx.test.uiautomator)
    implementation(libs.androidx.benchmark.macro)
}
