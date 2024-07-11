package dev.fobo66.factcheckerassistant.util

import android.content.Context
import androidx.core.os.ConfigurationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

interface LocaleProvider {
    val currentLocale: Locale
}

class LocaleProviderImpl @Inject constructor(@ApplicationContext private val context: Context) :
    LocaleProvider {
    override val currentLocale: Locale
        get() = ConfigurationCompat.getLocales(context.resources.configuration).get(0)
            ?: Locale.getDefault()
}
