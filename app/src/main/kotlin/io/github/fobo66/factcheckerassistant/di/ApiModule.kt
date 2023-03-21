package io.github.fobo66.factcheckerassistant.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.fobo66.factcheckerassistant.api.FactCheckApi
import io.github.fobo66.factcheckerassistant.util.LocalDateTimeAdapter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(LocalDateTimeAdapter())
            .build()

    @Provides
    @Singleton
    fun provideFactCheckApi(moshi: Moshi) = Retrofit.Builder()
        .baseUrl("https://factchecktools.googleapis.com")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create<FactCheckApi>()
}
