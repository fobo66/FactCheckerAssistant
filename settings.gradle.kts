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
            library("truth", "com.google.truth:truth:1.2.0")
            library("hamcrest", "org.hamcrest:hamcrest-core:2.2")
        }
    }
}

rootProject.name = "Fact Checker Assistant"
include(":app", ":composemd", ":baselineprofile")
