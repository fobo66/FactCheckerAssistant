pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        register("libs") {
            version("kotlin", "1.8.0")
            version("moshi", "1.14.0")
            version("retrofit", "2.9.0")
            version("coroutines", "1.6.4")
            library("material", "com.google.android.material:material:1.8.0")
            library("retrofit", "com.squareup.retrofit2", "retrofit").versionRef("retrofit")
            library("retrofit.moshi", "com.squareup.retrofit2", "converter-moshi").versionRef("retrofit")
            library("retrofit.mock", "com.squareup.retrofit2", "retrofit-mock").versionRef("retrofit")
            library("leakcanary", "com.squareup.leakcanary:leakcanary-android:2.10")
            library("commonmark", "org.commonmark:commonmark:0.21.0")
            library("timber", "com.jakewharton.timber:timber:5.0.1")
            library("coil", "io.coil-kt:coil-compose:2.2.2")
            library("collections", "org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")
            library("desugar", "com.android.tools:desugar_jdk_libs:2.0.2")
            library(
                "coroutines",
                "org.jetbrains.kotlinx",
                "kotlinx-coroutines-android"
            )
                .versionRef("coroutines")
            library(
                "coroutines.core",
                "org.jetbrains.kotlinx",
                "kotlinx-coroutines-core"
            )
                .versionRef("coroutines")
            library(
                "coroutines-test",
                "org.jetbrains.kotlinx",
                "kotlinx-coroutines-test"
            ).versionRef("coroutines")
            library(
                "moshi",
                "com.squareup.moshi",
                "moshi"
            ).versionRef("moshi")
            library(
                "moshi-codegen",
                "com.squareup.moshi",
                "moshi-kotlin-codegen"
            ).versionRef("moshi")
        }

        register("androidx") {
            version("plugin", "8.1.0-alpha09")
            version("lifecycle", "2.6.0")
            version("navigation", "2.6.0-alpha06")
            version("paging", "3.2.0-alpha04")
            version("uitest", "1.5.2")
            version("espresso", "3.5.1")
            version("benchmark", "1.2.0-alpha11")
            plugin("application", "com.android.application").versionRef("plugin")
            plugin("library", "com.android.library").versionRef("plugin")
            library("core", "androidx.core:core-ktx:1.9.0")
            library("annotations", "androidx.annotation:annotation:1.6.0")
            library("activity", "androidx.activity:activity-compose:1.7.0-rc01")
            library("appstartup", "androidx.startup:startup-runtime:1.2.0-alpha02")
            library("datastore", "androidx.datastore:datastore-preferences:1.0.0")
            library(
                "navigation",
                "androidx.navigation",
                "navigation-compose"
            ).versionRef("navigation")
            library(
                "navigation.testing",
                "androidx.navigation",
                "navigation-testing"
            ).versionRef("navigation")
            library("multidex", "androidx.multidex:multidex:2.0.1")
            library("splashscreen", "androidx.core:core-splashscreen:1.0.0")
            library("window", "androidx.window:window:1.1.0-alpha06")
            library("paging", "androidx.paging", "paging-runtime-ktx").versionRef("paging")
            library("paging.common", "androidx.paging", "paging-common-ktx").versionRef("paging")
            library("paging.compose", "androidx.paging:paging-compose:1.0.0-alpha18")
            library("lifecycle", "androidx.lifecycle", "lifecycle-runtime-compose").versionRef(
                "lifecycle"
            )
            library("viewmodel", "androidx.lifecycle", "lifecycle-viewmodel-compose").versionRef(
                "lifecycle"
            )
            library("uitest.core", "androidx.test", "core-ktx").versionRef("uitest")
            library("uitest.runner", "androidx.test", "runner").versionRef("uitest")
            library("uitest.rules", "androidx.test", "rules").versionRef("uitest")
            library(
                "uitest.espresso",
                "androidx.test.espresso",
                "espresso-core"
            ).versionRef("espresso")
            library(
                "uitest.espresso.contrib",
                "androidx.test.espresso",
                "espresso-contrib"
            ).versionRef("espresso")
            library(
                "uitest.espresso.intents",
                "androidx.test.espresso",
                "espresso-intents"
            ).versionRef("espresso")

            library("uitest.junit", "androidx.test.ext:junit-ktx:1.1.5")
            library("uitest.automator", "androidx.test.uiautomator:uiautomator:2.3.0-alpha02")
            library(
                "uitest.benchmark",
                "androidx.benchmark",
                "benchmark-junit4"
            ).versionRef("benchmark")
            library(
                "uitest.macrobenchmark",
                "androidx.benchmark",
                "benchmark-macro-junit4"
            ).versionRef("benchmark")
        }

        register("okhttp") {
            version("okhttp", "5.0.0-alpha.11")
            library("bom", "com.squareup.okhttp3", "okhttp-bom").versionRef("okhttp")
            library("core", "com.squareup.okhttp3", "okhttp").withoutVersion()
            library("logging", "com.squareup.okhttp3", "logging-interceptor").withoutVersion()
        }

        register("compose") {
            version("compiler", "1.4.0")
            version("compose", "1.4.0-rc01")
            version("material", "1.1.0-alpha08")
            library("ui", "androidx.compose.ui", "ui").versionRef("compose")
            library("preview", "androidx.compose.ui", "ui-tooling-preview").versionRef("compose")
            library("tooling", "androidx.compose.ui", "ui-tooling").versionRef("compose")
            library("testing", "androidx.compose.ui", "ui-test-junit4").versionRef("compose")
            library(
                "testing.manifest",
                "androidx.compose.ui",
                "ui-test-manifest"
            ).versionRef("compose")
            library("material", "androidx.compose.material3", "material3").versionRef("material")
            library(
                "windowsize",
                "androidx.compose.material3",
                "material3-window-size-class"
            ).versionRef(
                "material"
            )
        }

        register("accompanist") {
            version("accompanist", "0.29.2-rc")
            library(
                "swiperefresh",
                "com.google.accompanist",
                "accompanist-swiperefresh"
            ).versionRef("accompanist")
            library(
                "permissions",
                "com.google.accompanist",
                "accompanist-permissions"
            ).versionRef("accompanist")
            library(
                "systemuicontroller",
                "com.google.accompanist",
                "accompanist-systemuicontroller"
            ).versionRef("accompanist")
        }

        register("di") {
            version("hilt", "2.45")
            plugin("hilt", "com.google.dagger.hilt.android").versionRef("hilt")
            library("core", "com.google.dagger", "hilt-android").versionRef("hilt")
            library("compiler", "com.google.dagger", "hilt-android-compiler").versionRef("hilt")
            library("navigation", "androidx.hilt:hilt-navigation-compose:1.0.0")
        }

        register("testing") {
            version("kaspresso", "1.5.1")
            version("mockk", "1.13.4")
            library("junit", "org.junit.jupiter:junit-jupiter:5.9.2")
            library("junit4", "junit:junit:4.13.2")
            library("kakao", "io.github.kakaocup:compose:0.2.2")
            library(
                "kaspresso",
                "com.kaspersky.android-components",
                "kaspresso"
            ).versionRef("kaspresso")
            library(
                "kaspresso.compose",
                "com.kaspersky.android-components",
                "kaspresso-compose-support"
            ).versionRef("kaspresso")
            library("turbine", "app.cash.turbine:turbine:0.12.1")
            library("truth", "com.google.truth:truth:1.1.3")
            library("hamcrest", "org.hamcrest:hamcrest-core:2.2")
            library("mockk", "io.mockk", "mockk").versionRef("mockk")
            library("mockk.agent", "io.mockk", "mockk-agent-jvm").versionRef("mockk")
        }

        register("analysis") {
            version("detekt", "1.22.0")
            plugin("detekt", "io.gitlab.arturbosch.detekt").versionRef("detekt")
            library(
                "formatting",
                "io.gitlab.arturbosch.detekt",
                "detekt-formatting"
            ).versionRef("detekt")
            library("compose", "io.nlopez.compose.rules:detekt:0.1.5")
        }
    }
}

rootProject.name = "Fact Checker Assistant"
include(":app", ":composemd")
