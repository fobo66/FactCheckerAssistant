package dev.fobo66.factcheckerassistant.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.fobo66.factcheckerassistant.api.FactCheckApi
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

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
    fun provideFactCheckApi(json: Json) = Retrofit.Builder()
        .baseUrl("https://factchecktools.googleapis.com")
        .addConverterFactory(json.asConverterFactory("text/json".toMediaType()))
        .build()
        .create<FactCheckApi>()
}
