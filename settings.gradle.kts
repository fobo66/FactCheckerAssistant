pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version ("0.7.0")
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
    }
    versionCatalogs {
        register("libs") {
            version("kotlin", "1.9.20")
            version("moshi", "1.15.0")
            version("retrofit", "2.9.0")
            version("coroutines", "1.7.3")
            version("ksp", "1.9.20-1.0.14")
            plugin("ksp", "com.google.devtools.ksp").versionRef("ksp")
            library("material", "com.google.android.material:material:1.10.0")
            library("retrofit", "com.squareup.retrofit2", "retrofit").versionRef("retrofit")
            library("retrofit.moshi", "com.squareup.retrofit2", "converter-moshi").versionRef("retrofit")
            library("retrofit.kotlinx", "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
            library("retrofit.mock", "com.squareup.retrofit2", "retrofit-mock").versionRef("retrofit")
            library("leakcanary", "com.squareup.leakcanary:leakcanary-android:2.12")
            library("commonmark", "org.commonmark:commonmark:0.21.0")
            library("timber", "com.jakewharton.timber:timber:5.0.1")
            library("coil", "io.coil-kt:coil-compose:2.5.0")
            library("collections", "org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.6")
            library("desugar", "com.android.tools:desugar_jdk_libs:2.0.4")
            library("kotlinx.serialization", "org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.1")
            library("kotlinx.datetime", "org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
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
            version("plugin", "8.3.0-alpha14")
            version("lifecycle", "2.6.2")
            version("navigation", "2.7.5")
            version("paging", "3.2.1")
            version("uitest", "1.5.2")
            version("espresso", "3.6.0-alpha01")
            version("benchmark", "1.2.1")
            plugin("application", "com.android.application").versionRef("plugin")
            plugin("library", "com.android.library").versionRef("plugin")
            plugin("test", "com.android.test").versionRef("plugin")
            plugin("baseline-profile", "androidx.baselineprofile").version("1.2.1")
            library("core", "androidx.core:core-ktx:1.12.0")
            library("annotations", "androidx.annotation:annotation:1.7.0")
            library("activity", "androidx.activity:activity-compose:1.8.1")
            library("appstartup", "androidx.startup:startup-runtime:1.2.0-alpha02")
            library("datastore", "androidx.datastore:datastore-preferences:1.1.0-alpha06")
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
            library("splashscreen", "androidx.core:core-splashscreen:1.0.1")
            library("window", "androidx.window:window:1.2.0")
            library("paging", "androidx.paging", "paging-runtime-ktx").versionRef("paging")
            library("paging.common", "androidx.paging", "paging-common-ktx").versionRef("paging")
            library("paging.compose", "androidx.paging:paging-compose:3.2.1")
            library("lifecycle", "androidx.lifecycle", "lifecycle-runtime-compose").versionRef(
                "lifecycle"
            )
            library("viewmodel", "androidx.lifecycle", "lifecycle-viewmodel-compose").versionRef(
                "lifecycle"
            )
            library("tracing", "androidx.tracing:tracing:1.3.0-alpha02")
            library("uitest.core", "androidx.test:core-ktx:1.6.0-alpha02")
            library("uitest.runner", "androidx.test:runner:1.6.0-alpha04")
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

            library("uitest.junit", "androidx.test.ext:junit-ktx:1.2.0-alpha02")
            library("uitest.automator", "androidx.test.uiautomator:uiautomator:2.3.0-alpha05")
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
            version("compiler", "1.5.4")
            library("bom", "dev.chrisbanes.compose:compose-bom:2023.11.00-alpha02")
            library("ui", "androidx.compose.ui", "ui").withoutVersion()
            library("preview", "androidx.compose.ui", "ui-tooling-preview").withoutVersion()
            library("tooling", "androidx.compose.ui", "ui-tooling").withoutVersion()
            library("testing", "androidx.compose.ui", "ui-test-junit4").withoutVersion()
            library(
                "testing.manifest",
                "androidx.compose.ui",
                "ui-test-manifest"
            ).withoutVersion()
            library("material", "androidx.compose.material3", "material3").withoutVersion()
            library(
                "windowsize",
                "androidx.compose.material3",
                "material3-window-size-class"
            ).withoutVersion()
        }

        register("accompanist") {
            version("accompanist", "0.32.0")
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
            version("hilt", "2.48.1")
            plugin("hilt", "com.google.dagger.hilt.android").versionRef("hilt")
            library("core", "com.google.dagger", "hilt-android").versionRef("hilt")
            library("compiler", "com.google.dagger", "hilt-android-compiler").versionRef("hilt")
            library("navigation", "androidx.hilt:hilt-navigation-compose:1.1.0")
        }

        register("testing") {
            version("kaspresso", "1.5.3")
            version("mockk", "1.13.8")
            plugin("junit", "de.mannodermaus.android-junit5").version("1.10.0.0")
            library("junit", "org.junit.jupiter:junit-jupiter-api:5.10.1")
            library("junit.engine", "org.junit.jupiter:junit-jupiter-engine:5.10.1")
            library("junit4", "junit:junit:4.13.2")
            library("kakao", "io.github.kakaocup:compose:0.3.0")
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
            library("turbine", "app.cash.turbine:turbine:1.0.0")
            library("truth", "com.google.truth:truth:1.1.5")
            library("hamcrest", "org.hamcrest:hamcrest-core:2.2")
            library("mockk", "io.mockk", "mockk").versionRef("mockk")
            library("mockk.agent", "io.mockk", "mockk-agent-jvm").versionRef("mockk")
        }

        register("analysis") {
            version("detekt", "1.23.4")
            plugin("detekt", "io.gitlab.arturbosch.detekt").versionRef("detekt")
            library(
                "formatting",
                "io.gitlab.arturbosch.detekt",
                "detekt-formatting"
            ).versionRef("detekt")
            library("compose", "io.nlopez.compose.rules:detekt:0.3.3")
        }
    }
}

rootProject.name = "Fact Checker Assistant"
include(":app", ":composemd")
include(":baselineprofile")
