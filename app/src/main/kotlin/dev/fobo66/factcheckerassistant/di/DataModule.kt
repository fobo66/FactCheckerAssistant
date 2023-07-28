package dev.fobo66.factcheckerassistant.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.fobo66.factcheckerassistant.util.LocaleProvider
import dev.fobo66.factcheckerassistant.util.LocaleProviderImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindLocaleProvider(localeProviderImpl: LocaleProviderImpl): LocaleProvider
}
