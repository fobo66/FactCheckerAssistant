package dev.fobo66.factcheckerassistant.di

import de.jensklingenberg.ktorfit.Ktorfit
import dev.fobo66.factcheckerassistant.api.FactCheckApi
import dev.fobo66.factcheckerassistant.api.createFactCheckApi
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.CompressionInterceptor
import okhttp3.Gzip
import okhttp3.brotli.Brotli
import okhttp3.zstd.Zstd

@ContributesTo(AppScope::class)
@BindingContainer
object ApiModule {

    @Provides
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    fun provideHttpClient(json: Json): HttpClient = HttpClient(OkHttp) {
        engine {
            duplexStreamingEnabled = true
            addInterceptor(CompressionInterceptor(Zstd, Brotli, Gzip))
        }
        expectSuccess = true
        install(ContentNegotiation) {
            json(json)
        }
    }

    @Provides
    fun provideFactCheckApi(httpClient: HttpClient): FactCheckApi = Ktorfit.Builder()
        .baseUrl("https://factchecktools.googleapis.com/")
        .httpClient(httpClient)
        .build()
        .createFactCheckApi()
}
