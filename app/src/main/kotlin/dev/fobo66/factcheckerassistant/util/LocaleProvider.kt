package dev.fobo66.factcheckerassistant.util

import android.content.Context
import androidx.core.os.ConfigurationCompat
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import java.util.Locale

interface LocaleProvider {
    val currentLocale: Locale
}

@ContributesBinding(AppScope::class)
class LocaleProviderImpl @Inject constructor(private val context: Context) : LocaleProvider {
    override val currentLocale: Locale
        get() = ConfigurationCompat.getLocales(context.resources.configuration).get(0)
            ?: Locale.getDefault()
}
