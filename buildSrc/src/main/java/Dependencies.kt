/*
 *    Copyright 2020 Andrey Mukamolov
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

object Versions {
    const val kotlin = "1.3.72"
    const val kotlinCoroutines = "1.3.5"
    const val koin = "2.1.5"
    const val moshi = "1.9.2"
    const val retrofit = "2.8.1"
    const val mockk = "1.9.3"
    const val okhttp = "4.5.0"
    const val paging = "2.1.2"
}

object Dependencies {
    val kotlinStandardLibrary = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    val kotlinCoroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}"
    val kotlinCoroutinesTest =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinCoroutines}"
    val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    val koinScope = "org.koin:koin-androidx-scope:${Versions.koin}"
    val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    val koinFragment = "org.koin:koin-androidx-fragment:${Versions.koin}"
    val mockk = "io.mockk:mockk:${Versions.mockk}"
    val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp}"
    val paging = "androidx.paging:paging-runtime-ktx:${Versions.paging}"
    val pagingCommon = "androidx.paging:paging-common-ktx:${Versions.paging}"
}