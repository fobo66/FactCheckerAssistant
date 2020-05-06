package io.github.fobo66.factcheckerassistant.util

import android.content.Context
import androidx.core.os.ConfigurationCompat
import java.util.*


interface LocaleProvider {
    val currentLocale: Locale
}

class LocaleProviderImpl(private val context: Context) : LocaleProvider {
    override val currentLocale: Locale
        get() = ConfigurationCompat.getLocales(context.resources.getConfiguration()).get(0)
}
