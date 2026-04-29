package dev.fobo66.factcheckerassistant

import android.app.Application
import dev.fobo66.factcheckerassistant.di.AppGraph
import dev.zacsweers.metro.createGraphFactory
import dev.zacsweers.metrox.android.MetroAppComponentProviders
import dev.zacsweers.metrox.android.MetroApplication
import timber.log.Timber

class App :
    Application(),
    MetroApplication {

    private val appGraph by lazy { createGraphFactory<AppGraph.Factory>().create(this) }
    override val appComponentProviders: MetroAppComponentProviders
        get() = appGraph

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
