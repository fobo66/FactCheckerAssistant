plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("io.gitlab.arturbosch.detekt")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId = "io.github.fobo66.factcheckerassistant"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "API_KEY", "\"${System.getenv("FACTCHECK_API_KEY")}\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.72")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.7")
    implementation("androidx.core:core:1.5.0-alpha01")
    implementation("androidx.core:core-ktx:1.5.0-alpha01")
    implementation("androidx.appcompat:appcompat:1.3.0-alpha01")
    implementation("androidx.constraintlayout:constraintlayout:2.0.0-rc1")
    implementation("com.google.android.material:material:1.3.0-alpha02")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.0-alpha06")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.0-alpha06")
    implementation("androidx.paging:paging-runtime-ktx:3.0.0-alpha04")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.0")

    val koinVersion = "2.1.6"
    implementation("org.koin:koin-android:${koinVersion}")
    implementation("org.koin:koin-androidx-scope:$koinVersion")
    implementation("org.koin:koin-androidx-viewmodel:${koinVersion}")
    implementation("org.koin:koin-androidx-fragment:$koinVersion")

    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:${retrofitVersion}")

    val moshiVersion = "1.9.3"
    implementation("com.squareup.moshi:moshi:${moshiVersion}")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:${moshiVersion}")

    implementation("com.jakewharton.timber:timber:4.7.1")

    testImplementation("junit:junit:4.13")
    testImplementation("io.mockk:mockk:1.10.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.7")
    testImplementation("androidx.paging:paging-common-ktx:3.0.0-alpha04")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("androidx.navigation:navigation-testing:2.3.0")
    testImplementation("com.squareup.retrofit2:retrofit-mock:$retrofitVersion")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}
