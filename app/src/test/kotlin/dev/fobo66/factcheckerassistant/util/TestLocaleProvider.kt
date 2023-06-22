package dev.fobo66.factcheckerassistant.util

import dev.fobo66.factcheckerassistant.util.LocaleProvider
import java.util.Locale

class TestLocaleProvider : LocaleProvider {
    override val currentLocale: Locale
        get() = Locale.getDefault()
}
