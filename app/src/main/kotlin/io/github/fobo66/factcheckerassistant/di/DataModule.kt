package io.github.fobo66.factcheckerassistant.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.fobo66.factcheckerassistant.util.LocaleProvider
import io.github.fobo66.factcheckerassistant.util.LocaleProviderImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindLocaleProvider(localeProviderImpl: LocaleProviderImpl): LocaleProvider
}
