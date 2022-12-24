import com.android.sdklib.AndroidVersion.VersionCodes

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("io.gitlab.arturbosch.detekt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "io.github.fobo66.factcheckerassistant"
        minSdk = VersionCodes.LOLLIPOP
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "API_KEY", "\"${System.getenv("FACTCHECK_API_KEY")}\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
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

    packagingOptions {
        resources {
            excludes += listOf("META-INF/AL2.0", "META-INF/LGPL2.1")
        }
    }
    namespace = "io.github.fobo66.factcheckerassistant"
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))
    implementation(libs.coroutines)
    implementation(androidx.core)
    implementation(libs.material)
    implementation(androidx.activity)
    implementation(androidx.viewmodel)
    implementation(androidx.lifecycle)
    implementation(androidx.paging)

    implementation(compose.ui)
    implementation(compose.material)
    implementation(compose.preview)
    implementation(compose.windowsize)
    androidTestImplementation(compose.testing)
    debugImplementation(compose.testing.manifest)
    debugImplementation(compose.tooling)
    implementation(androidx.paging.compose)
    implementation(androidx.navigation)
    implementation(di.navigation)
    implementation(project(":composemd"))

    implementation(di.core)
    kapt(di.compiler)

    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)

    implementation(libs.moshi)
    kapt(libs.moshi.codegen)

    implementation(libs.timber)

    coreLibraryDesugaring(libs.desugar)

    detektPlugins(analysis.formatting)
    detektPlugins(analysis.compose)

    testImplementation(testing.junit4)
    testImplementation(libs.coroutines.test)
    testImplementation(androidx.paging.common)
    testImplementation(libs.retrofit.mock)
    androidTestImplementation(androidx.navigation.testing)
    androidTestImplementation(androidx.uitest.junit)
    androidTestImplementation(androidx.uitest.espresso)
}
