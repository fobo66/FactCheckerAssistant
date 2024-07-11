import com.android.sdklib.AndroidVersion.VersionCodes

plugins {
    alias(libs.plugins.android.library)
    kotlin("android")
    kotlin("plugin.compose")
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlinter)
}

android {
    compileSdk = VersionCodes.UPSIDE_DOWN_CAKE

    defaultConfig {
        minSdk = VersionCodes.N

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    buildFeatures {
        compose = true
    }

    compileOptions {
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

    testOptions {
        animationsDisabled = true
    }

    namespace = "dev.fobo66.composemd"
}

dependencies {
    implementation(libs.commonmark)
    implementation(libs.androidx.tracing)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.compose.ui.preview)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.testing)
    debugImplementation(libs.compose.ui.testing.manifest)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.coil)

    detektPlugins(libs.detekt.rules.formatting)
    detektPlugins(libs.detekt.rules.compose)

    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.espresso.core)
}
