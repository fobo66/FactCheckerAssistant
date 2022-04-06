package io.github.fobo66.factcheckerassistant.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.fobo66.factcheckerassistant.api.FactCheckApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideFactCheckApi() = Retrofit.Builder()
        .baseUrl("https://factchecktools.googleapis.com")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create<FactCheckApi>()
}
