package io.github.fobo66.factcheckerassistant

import android.app.Application
import io.github.fobo66.factcheckerassistant.ui.main.MainFragment
import io.github.fobo66.factcheckerassistant.ui.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    private val fragmentModule = module {
        fragment { MainFragment() }
    }

    private val viewModelsModule = module {
        viewModel { MainViewModel() }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            fragmentFactory()
            modules(fragmentModule, viewModelsModule)
        }
    }
}