package io.github.fobo66.factcheckerassistant.ui.main

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import io.github.fobo66.factcheckerassistant.R
import io.github.fobo66.factcheckerassistant.databinding.MainActivityBinding
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinExperimentalAPI

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var binding: MainActivityBinding

    private val navController: NavController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navHostFragment.navController
    }

    @KoinExperimentalAPI
    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding.bottomNavBar.setupWithNavController(navController)
        binding.bottomNavBar.applySystemWindowInsetsToPadding(bottom = true)

        processSearch(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        processSearch(intent)
    }

    private fun processSearch(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            mainViewModel.search(query)
        }
    }
}
