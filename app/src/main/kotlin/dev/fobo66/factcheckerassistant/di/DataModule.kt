package dev.fobo66.factcheckerassistant.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.fobo66.factcheckerassistant.util.LocaleProvider
import dev.fobo66.factcheckerassistant.util.LocaleProviderImpl
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import javax.inject.Singleton

@ContributesTo(AppScope::class)
@BindingContainer
interface DataModule {

    @Binds
    fun bindLocaleProvider(localeProviderImpl: LocaleProviderImpl): LocaleProvider
}
