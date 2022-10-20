import com.android.sdklib.AndroidVersion.VersionCodes

plugins {
    id("com.android.library")
    kotlin("android")
    id("io.gitlab.arturbosch.detekt")
}

val composeVersion = "1.3.0-rc01"
val composeCompilerVersion = "1.3.1"

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
    implementation("org.commonmark:commonmark:0.20.0")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.material3:material3:1.0.0-rc01")
    implementation("io.coil-kt:coil-compose:2.2.2")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.0")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.21.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.4-beta01")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0-beta01")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")
}
