import com.android.sdklib.AndroidVersion.VersionCodes

plugins {
    alias(libs.plugins.android.app)
    kotlin("android")
    kotlin("plugin.serialization")
    kotlin("plugin.compose")
    alias(libs.plugins.ksp)
    id("kotlin-parcelize")
    alias(libs.plugins.detekt)
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.junit)
    alias(libs.plugins.baseline.profile)
    alias(libs.plugins.kotlinter)
}

android {
    compileSdk = 35

    defaultConfig {
        applicationId = "dev.fobo66.factcheckerassistant"
        minSdk = VersionCodes.N
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "API_KEY", "\"${loadSecret(rootProject, API_KEY)}\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packaging {
        resources {
            excludes += listOf("META-INF/AL2.0", "META-INF/LGPL2.1")
        }
    }
    namespace = "dev.fobo66.factcheckerassistant"

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.collections)
    implementation(libs.androidx.core)
    implementation(libs.material.legacy)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.compose)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.splashscreen)
    implementation(libs.androidx.tracing)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.compose.ui.preview)
    implementation(libs.profileinstaller)
    androidTestImplementation(libs.compose.ui.testing)
    androidTestImplementation(platform(libs.compose.bom))
    baselineProfile(project(":baselineprofile"))
    debugImplementation(libs.compose.ui.testing.manifest)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.navigation)
    implementation(libs.hilt.navigation)
    implementation(project(":composemd"))

    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)

    implementation(libs.ktorfit)
    implementation(libs.ktor.cio)
    implementation(libs.ktor.content)
    implementation(libs.ktor.json)
    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlinx.datetime)

    implementation(libs.timber)
    debugImplementation(libs.leakcanary)

    detektPlugins(libs.detekt.rules.formatting)
    detektPlugins(libs.detekt.rules.compose)

    testImplementation(libs.junit.api)
    testImplementation(libs.truth)
    testRuntimeOnly(libs.junit.engine)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.paging.common)
    androidTestImplementation(libs.androidx.navigation.test)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.kaspresso)
    androidTestImplementation(libs.kaspresso.compose)
}
