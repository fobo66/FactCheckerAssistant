import com.android.sdklib.AndroidVersion.VersionCodes

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("io.gitlab.arturbosch.detekt")
    id("dagger.hilt.android.plugin")
}

val composeVersion = "1.4.0-alpha02"
val composeCompilerVersion = "1.3.2"

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
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = composeCompilerVersion
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
    val lifecycleVersion = "2.6.0-alpha03"
    val activityVersion = "1.7.0-alpha02"
    val pagingVersion = "3.2.0-alpha03"
    val navigationVersion = "2.5.3"
    val coroutinesVersion = "1.6.4"

    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("com.google.android.material:material:1.8.0-alpha02")
    implementation("androidx.activity:activity-compose:$activityVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.material3:material3:1.1.0-alpha02")
    implementation("androidx.compose.runtime:runtime:$composeVersion")
    implementation("androidx.paging:paging-compose:1.0.0-alpha17")
    implementation("androidx.navigation:navigation-compose:$navigationVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation(project(":composemd"))

    val hiltVersion = "2.44.2"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")

    val moshiVersion = "1.14.0"
    implementation("com.squareup.moshi:moshi:$moshiVersion")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion")

    implementation("com.jakewharton.timber:timber:5.0.1")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.0")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.21.0")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    testImplementation("androidx.paging:paging-common-ktx:$pagingVersion")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("androidx.navigation:navigation-testing:$navigationVersion")
    testImplementation("com.squareup.retrofit2:retrofit-mock:$retrofitVersion")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.4-rc01")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0-rc01")
}
