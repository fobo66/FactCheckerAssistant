package dev.fobo66.factcheckerassistant.di

import dagger.Provides
import de.jensklingenberg.ktorfit.Ktorfit
import dev.fobo66.factcheckerassistant.api.FactCheckApi
import dev.fobo66.factcheckerassistant.api.createFactCheckApi
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@ContributesTo(AppScope::class)
@BindingContainer
object ApiModule {

    @Provides
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    fun provideHttpClient(json: Json): HttpClient = HttpClient(CIO) {
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
