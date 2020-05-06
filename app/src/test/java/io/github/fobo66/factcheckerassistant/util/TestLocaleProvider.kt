package io.github.fobo66.factcheckerassistant.util

import java.util.*

class TestLocaleProvider : LocaleProvider {
    override val currentLocale: Locale
        get() = Locale.getDefault()
}
