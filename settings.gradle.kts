pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version ("0.8.0")
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
    }
    versionCatalogs {
        register("libs") {
            version("kotlin", "1.9.22")
            version("moshi", "1.15.0")
            version("retrofit", "2.9.0")
            version("coroutines", "1.7.3")
            version("ksp", "1.9.22-1.0.16")
            plugin("ksp", "com.google.devtools.ksp").versionRef("ksp")
            library("material", "com.google.android.material:material:1.11.0")
            library("retrofit", "com.squareup.retrofit2", "retrofit").versionRef("retrofit")
            library("retrofit.moshi", "com.squareup.retrofit2", "converter-moshi").versionRef("retrofit")
            library("retrofit.kotlinx", "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
            library("retrofit.mock", "com.squareup.retrofit2", "retrofit-mock").versionRef("retrofit")
            library("leakcanary", "com.squareup.leakcanary:leakcanary-android:2.13")
            library("commonmark", "org.commonmark:commonmark:0.21.0")
            library("timber", "com.jakewharton.timber:timber:5.0.1")
            library("coil", "io.coil-kt:coil-compose:2.5.0")
            library("collections", "org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.7")
            library("desugar", "com.android.tools:desugar_jdk_libs:2.0.4")
            library("kotlinx.serialization", "org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
            library("kotlinx.datetime", "org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
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
        }

        register("androidx") {
            version("uitest", "1.5.2")
            version("espresso", "3.6.0-alpha02")
            version("benchmark", "1.2.2")
            library("uitest.core", "androidx.test:core-ktx:1.6.0-alpha04")
            library("uitest.runner", "androidx.test:runner:1.6.0-alpha05")
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
            library("uitest.automator", "androidx.test.uiautomator:uiautomator:2.3.0-beta01")
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

        register("compose") {
            version("compiler", "1.5.8")
            library("bom", "dev.chrisbanes.compose:compose-bom:2024.01.00-alpha01")
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

        register("testing") {
            version("kaspresso", "1.5.3")
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
            library("truth", "com.google.truth:truth:1.3.0")
            library("hamcrest", "org.hamcrest:hamcrest-core:2.2")
        }
    }
}

rootProject.name = "Fact Checker Assistant"
include(":app", ":composemd")
include(":baselineprofile")
