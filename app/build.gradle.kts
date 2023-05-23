import com.android.sdklib.AndroidVersion.VersionCodes

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("io.gitlab.arturbosch.detekt")
    id("dagger.hilt.android.plugin")
    id("de.mannodermaus.android-junit5")
}

android {
    compileSdk = VersionCodes.TIRAMISU

    defaultConfig {
        applicationId = "io.github.fobo66.factcheckerassistant"
        minSdk = VersionCodes.LOLLIPOP
        targetSdkPreview = "UpsideDownCake"
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

    packaging {
        resources {
            excludes += listOf("META-INF/AL2.0", "META-INF/LGPL2.1")
        }
    }
    namespace = "io.github.fobo66.factcheckerassistant"
}

kotlin {
    jvmToolchain(11)
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))
    implementation(libs.coroutines)
    implementation(libs.collections)
    implementation(androidx.core)
    implementation(libs.material)
    implementation(androidx.activity)
    implementation(androidx.viewmodel)
    implementation(androidx.lifecycle)
    implementation(androidx.paging)

    implementation(platform(compose.bom))
    implementation(compose.ui)
    implementation(compose.material)
    implementation(compose.preview)
    implementation(compose.windowsize)
    androidTestImplementation(compose.testing)
    androidTestImplementation(platform(compose.bom))
    debugImplementation(compose.testing.manifest)
    debugImplementation(compose.tooling)
    implementation(androidx.paging.compose)
    implementation(androidx.navigation)
    implementation(di.navigation)
    implementation(project(":composemd"))

    implementation(di.core)
    kapt(di.compiler)

    implementation(libs.retrofit)
    implementation(platform(okhttp.bom))
    implementation(okhttp.core)
    implementation(libs.retrofit.moshi)
    implementation(libs.moshi)
    kapt(libs.moshi.codegen)

    implementation(libs.timber)
    implementation(libs.leakcanary)

    coreLibraryDesugaring(libs.desugar)

    detektPlugins(analysis.formatting)
    detektPlugins(analysis.compose)

    testImplementation(testing.junit)
    testRuntimeOnly(testing.junit.engine)
    testImplementation(libs.coroutines.test)
    testImplementation(androidx.paging.common)
    testImplementation(libs.retrofit.mock)
    androidTestImplementation(androidx.navigation.testing)
    androidTestImplementation(androidx.uitest.junit)
    androidTestImplementation(androidx.uitest.espresso)
}
