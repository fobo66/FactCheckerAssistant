import com.android.sdklib.AndroidVersion.VersionCodes

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("io.gitlab.arturbosch.detekt")
    id("dagger.hilt.android.plugin")
}

val composeVersion = "1.0.1"

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
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }

    buildTypes {
        debug {
            multiDexEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            multiDexKeepProguard = file("multidex-keep.pro")
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
    val lifecycleVersion = "2.3.1"
    val activityVersion = "1.3.1"
    val fragmentVersion = "1.4.0-alpha06"
    val pagingVersion = "3.0.1"
    val navigationVersion = "2.4.0-alpha07"
    val coroutinesVersion = "1.5.1"

    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("androidx.core:core-ktx:1.7.0-alpha01")
    implementation("androidx.activity:activity-ktx:$activityVersion")
    implementation("androidx.fragment:fragment-ktx:$fragmentVersion")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.material:material-icons-core:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")
    implementation("com.google.android.material:compose-theme-adapter:$composeVersion")
    implementation("androidx.paging:paging-compose:1.0.0-alpha12")
    implementation("androidx.navigation:navigation-compose:$navigationVersion")
    implementation("androidx.activity:activity-compose:$activityVersion")
    implementation(project(":composemd"))

    implementation("dev.chrisbanes.insetter:insetter:0.6.0")
    implementation("com.google.accompanist:accompanist-insets:0.16.1")

    val hiltVersion = "2.38.1"
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
