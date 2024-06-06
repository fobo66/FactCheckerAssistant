package dev.fobo66.factcheckerassistant.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.jensklingenberg.ktorfit.Ktorfit
import dev.fobo66.factcheckerassistant.api.FactCheckApi
import dev.fobo66.factcheckerassistant.api.createFactCheckApi
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    @Singleton
    fun provideHttpClient(json: Json): HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(json)
        }
    }

    @Provides
    @Singleton
    fun provideFactCheckApi(httpClient: HttpClient): FactCheckApi = Ktorfit.Builder()
        .baseUrl("https://factchecktools.googleapis.com/")
        .httpClient(httpClient)
        .build()
        .createFactCheckApi()
}
