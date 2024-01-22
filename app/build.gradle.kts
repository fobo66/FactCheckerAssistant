import com.android.sdklib.AndroidVersion.VersionCodes

plugins {
    alias(libs.plugins.android.app)
    kotlin("android")
    kotlin("plugin.serialization")
    alias(libs.plugins.ksp)
    id("kotlin-parcelize")
    alias(libs.plugins.detekt)
    id("dagger.hilt.android.plugin")
    id("de.mannodermaus.android-junit5")
    alias(libs.plugins.baseline.profile)
}

android {
    compileSdk = VersionCodes.UPSIDE_DOWN_CAKE

    defaultConfig {
        applicationId = "dev.fobo66.factcheckerassistant"
        minSdk = VersionCodes.N
        targetSdk = VersionCodes.UPSIDE_DOWN_CAKE
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "API_KEY", "\"${loadSecret(rootProject, API_KEY)}\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
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

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
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

kotlin {
    jvmToolchain(17)
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.incremental", "true")
    arg("room.expandProjection", "true")
    arg("room.generateKotlin", "true")
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

    implementation(libs.retrofit)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp.core)
    implementation(libs.retrofit.kotlinx)
    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlinx.datetime)

    implementation(libs.timber)
    debugImplementation(libs.leakcanary)

    coreLibraryDesugaring(libs.desugar)

    detektPlugins(libs.detekt.rules.formatting)
    detektPlugins(libs.detekt.rules.compose)

    testImplementation(testing.junit)
    testRuntimeOnly(testing.junit.engine)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.paging.common)
    testImplementation(libs.retrofit.mock)
    androidTestImplementation(libs.androidx.navigation.test)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.espresso.core)
}
