import com.android.build.api.dsl.ManagedVirtualDevice

plugins {
    alias(androidx.plugins.test)
    kotlin("android")
    alias(androidx.plugins.baseline.profile)
}

android {
    namespace = "dev.fobo66.baselineprofile"
    compileSdk = 34

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    defaultConfig {
        minSdk = 28
        targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    targetProjectPath = ":app"

    testOptions.managedDevices.devices {
        create<ManagedVirtualDevice>("pixel6Api34") {
            device = "Pixel 6"
            apiLevel = 34
            systemImageSource = "google"
        }
    }
}

// This is the configuration block for the Baseline Profile plugin.
// You can specify to run the generators on a managed devices or connected devices.
baselineProfile {
    managedDevices += "pixel6Api34"
    useConnectedDevices = false
}

dependencies {
    implementation(androidx.uitest.junit)
    implementation(androidx.uitest.espresso)
    implementation(androidx.uitest.automator)
    implementation(androidx.uitest.macrobenchmark)
}
