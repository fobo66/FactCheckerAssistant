import com.android.build.api.dsl.ManagedVirtualDevice
import com.android.sdklib.AndroidVersion

plugins {
    alias(libs.plugins.android.test)
    kotlin("android")
    alias(libs.plugins.baseline.profile)
}

android {
    namespace = "dev.fobo66.baselineprofile"
    compileSdk = AndroidVersion.VersionCodes.UPSIDE_DOWN_CAKE

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    defaultConfig {
        minSdk = AndroidVersion.VersionCodes.P
        targetSdk = AndroidVersion.VersionCodes.UPSIDE_DOWN_CAKE

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    targetProjectPath = ":app"

    testOptions.managedDevices.devices {
        create<ManagedVirtualDevice>("pixel6Api34") {
            device = "Pixel 6"
            apiLevel = AndroidVersion.VersionCodes.UPSIDE_DOWN_CAKE
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
