import com.android.build.api.dsl.ManagedVirtualDevice
import com.android.sdklib.AndroidVersion.VersionCodes

plugins {
    alias(libs.plugins.android.test)
    kotlin("android")
    alias(libs.plugins.baseline.profile)
    alias(libs.plugins.kotlinter)
}

android {
    namespace = "dev.fobo66.baselineprofile"
    compileSdk = VersionCodes.VANILLA_ICE_CREAM

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    defaultConfig {
        minSdk = VersionCodes.P
        targetSdk = VersionCodes.UPSIDE_DOWN_CAKE

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    targetProjectPath = ":app"

    testOptions.managedDevices.allDevices {
        create<ManagedVirtualDevice>("pixel6Api34") {
            device = "Pixel 6"
            apiLevel = VersionCodes.UPSIDE_DOWN_CAKE
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
    implementation(libs.androidx.test.junit)
    implementation(libs.espresso.core)
    implementation(libs.androidx.test.uiautomator)
    implementation(libs.androidx.benchmark.macro)
}
