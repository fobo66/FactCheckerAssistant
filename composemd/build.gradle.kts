import com.android.sdklib.AndroidVersion.VersionCodes

plugins {
    id("com.android.library")
    kotlin("android")
    id("io.gitlab.arturbosch.detekt")
}

val composeVersion = "1.4.0-alpha03"
val composeCompilerVersion = "1.4.0-alpha02"

android {
    compileSdk = 33

    defaultConfig {
        minSdk = VersionCodes.LOLLIPOP
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
        kotlinCompilerExtensionVersion = composeCompilerVersion
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    packagingOptions {
        resources {
            excludes += "META-INF/AL2.0"
            excludes += "META-INF/LGPL2.1"
        }
    }
    namespace = "dev.fobo66.composemd"
}

dependencies {
    implementation(libs.commonmark)
    implementation(compose.ui)
    implementation(compose.material)
    implementation(compose.preview)
    androidTestImplementation(compose.testing)
    debugImplementation(compose.testing.manifest)
    debugImplementation(compose.tooling)
    implementation(libs.coil)

    coreLibraryDesugaring(libs.desugar)

    detektPlugins(analysis.formatting)
    detektPlugins(analysis.compose)

    testImplementation(testing.junit4)
    androidTestImplementation(androidx.uitest.junit)
    androidTestImplementation(androidx.uitest.espresso)
}
