import com.android.sdklib.AndroidVersion.VersionCodes

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("io.gitlab.arturbosch.detekt")
    id("dagger.hilt.android.plugin")
}

val composeVersion = "1.0.5"

android {
    compileSdk = VersionCodes.S

    defaultConfig {
        applicationId = "io.github.fobo66.factcheckerassistant"
        minSdk = VersionCodes.LOLLIPOP
        targetSdk = VersionCodes.S
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "API_KEY", "\"${System.getenv("FACTCHECK_API_KEY")}\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
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
            excludes += "META-INF/AL2.0"
            excludes += "META-INF/LGPL2.1"
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    val lifecycleVersion = "2.4.0"
    val activityVersion = "1.4.0"
    val pagingVersion = "3.0.1"
    val navigationVersion = "2.4.0-beta01"
    val coroutinesVersion = "1.5.2"

    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.activity:activity-ktx:$activityVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.material:material-icons-core:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")
    implementation("androidx.paging:paging-compose:1.0.0-alpha14")
    implementation("androidx.navigation:navigation-compose:$navigationVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0-alpha03")
    implementation("androidx.activity:activity-compose:$activityVersion")
    implementation(project(":composemd"))

    implementation("com.google.accompanist:accompanist-insets:0.20.2")

    val hiltVersion = "2.40"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")

    val moshiVersion = "1.12.0"
    implementation("com.squareup.moshi:moshi:$moshiVersion")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion")

    implementation("com.jakewharton.timber:timber:5.0.1")

    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    testImplementation("androidx.paging:paging-common-ktx:$pagingVersion")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("androidx.navigation:navigation-testing:$navigationVersion")
    testImplementation("com.squareup.retrofit2:retrofit-mock:$retrofitVersion")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
