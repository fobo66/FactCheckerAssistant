package io.github.fobo66.factcheckerassistant

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import io.github.fobo66.factcheckerassistant.api.FactCheckApi
import io.github.fobo66.factcheckerassistant.data.FactCheckRepository
import io.github.fobo66.factcheckerassistant.ui.main.MainFragment
import io.github.fobo66.factcheckerassistant.ui.main.MainViewModel
import io.github.fobo66.factcheckerassistant.util.LocaleProvider
import io.github.fobo66.factcheckerassistant.util.LocaleProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import timber.log.Timber

class App : Application() {

    private val fragmentModule = module {
        fragment { MainFragment() }
    }

    private val viewModelsModule = module {
        viewModel { (handle: SavedStateHandle) -> MainViewModel(get(), handle) }
    }

    private val apiModule = module {
        single {
            Retrofit.Builder()
                .baseUrl("https://factchecktools.googleapis.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create<FactCheckApi>()
        }
    }

    private val dataModule = module {
        single { FactCheckRepository(get(), get()) }
        single { LocaleProviderImpl(get()) } bind LocaleProvider::class
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@App)
            fragmentFactory()
            modules(fragmentModule, viewModelsModule, apiModule, dataModule)
        }
    }
}
