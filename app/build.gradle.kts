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
        kotlinCompilerExtensionVersion = compose.versions.compiler.get()
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
    implementation(libs.coroutines)
    implementation(libs.collections)
    implementation(libs.androidx.core)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.compose)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.tracing)

    implementation(platform(compose.bom))
    implementation(compose.ui)
    implementation(compose.material)
    implementation(compose.preview)
    implementation(compose.windowsize)
    implementation(libs.profileinstaller)
    androidTestImplementation(compose.testing)
    androidTestImplementation(platform(compose.bom))
    baselineProfile(project(":baselineprofile"))
    debugImplementation(compose.testing.manifest)
    debugImplementation(compose.tooling)
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
    testImplementation(libs.coroutines.test)
    testImplementation(libs.androidx.paging.common)
    testImplementation(libs.retrofit.mock)
    androidTestImplementation(libs.androidx.navigation.test)
    androidTestImplementation(androidx.uitest.junit)
    androidTestImplementation(androidx.uitest.espresso)
}
