package dev.fobo66.factcheckerassistant.util

import java.util.Locale

class TestLocaleProvider : LocaleProvider {
    override val currentLocale: Locale
        get() = Locale.getDefault()
}
