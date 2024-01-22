import com.android.sdklib.AndroidVersion.VersionCodes

plugins {
    alias(libs.plugins.android.library)
    kotlin("android")
    alias(libs.plugins.detekt)
}

android {
    compileSdk = VersionCodes.UPSIDE_DOWN_CAKE

    defaultConfig {
        minSdk = VersionCodes.N
        version = 1

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    packaging {
        resources {
            excludes += "META-INF/AL2.0"
            excludes += "META-INF/LGPL2.1"
        }
    }
    namespace = "dev.fobo66.composemd"
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.commonmark)
    implementation(libs.androidx.tracing)
    implementation(platform(compose.bom))
    implementation(compose.ui)
    implementation(compose.material)
    implementation(compose.preview)
    androidTestImplementation(platform(compose.bom))
    androidTestImplementation(compose.testing)
    debugImplementation(compose.testing.manifest)
    debugImplementation(compose.tooling)
    implementation(libs.coil)

    coreLibraryDesugaring(libs.desugar)

    detektPlugins(libs.detekt.rules.formatting)
    detektPlugins(libs.detekt.rules.compose)

    testImplementation(testing.junit4)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.espresso.core)
}
