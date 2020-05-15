plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("io.gitlab.arturbosch.detekt")
}

android {
    compileSdkVersion(29)

    defaultConfig {
        applicationId = "io.github.fobo66.factcheckerassistant"
        minSdkVersion(21)
        targetSdkVersion(29)
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
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.kotlinStandardLibrary)
    implementation(Dependencies.kotlinCoroutines)
    implementation(Dependencies.androidxCore)
    implementation(Dependencies.androidxCoreKtx)
    implementation(Dependencies.androidxAppcompat)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.material)
    implementation(Dependencies.viewModel)
    implementation(Dependencies.liveData)
    implementation(Dependencies.paging)

    implementation(Dependencies.koinAndroid)
    implementation(Dependencies.koinScope)
    implementation(Dependencies.koinViewModel)
    implementation(Dependencies.koinFragment)

    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitMoshiConverter)
    implementation(Dependencies.moshi)
    kapt(Dependencies.moshiCodegen)

    implementation(Dependencies.timber)

    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.mockk)
    testImplementation(Dependencies.mockWebServer)
    testImplementation(Dependencies.kotlinCoroutinesTest)
    testImplementation(Dependencies.pagingCommon)
    testImplementation(Dependencies.androidxCoreArchTesting)
    testImplementation(Dependencies.retrofitMock)
    androidTestImplementation(Dependencies.androidxJunit)
    androidTestImplementation(Dependencies.espresso)
}
