package io.github.fobo66.factcheckerassistant

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dev.fobo66.factcheckerassistant.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
