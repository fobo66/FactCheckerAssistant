package io.github.fobo66.factcheckerassistant.util

import androidx.lifecycle.Observer
import timber.log.Timber

class LoggingObserver<T> : Observer<T> {
    var value: T? = null
    override fun onChanged(t: T?) {
        Timber.d("Observed value: %s", t)
        this.value = t
    }
}