package io.github.fobo66.factcheckerassistant.ui.main

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.primarySurface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import io.github.fobo66.factcheckerassistant.R
import io.github.fobo66.factcheckerassistant.ui.guide.FactCheckGuide
import io.github.fobo66.factcheckerassistant.ui.list.ClaimsList
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            MdcTheme {
                ProvideWindowInsets {
                    Scaffold(
                        topBar = {
                            Surface(
                                color = MaterialTheme.colors.primarySurface,
                                elevation = topbarElevation
                            ) {
                                TopAppBar(
                                    title = {
                                        Text(
                                            stringResource(R.string.fact_check_guide_title)
                                        )
                                    },
                                    backgroundColor = Color.Transparent,
                                    contentColor = contentColorFor(MaterialTheme.colors.primarySurface),
                                    elevation = 0.dp,
                                    modifier = Modifier.statusBarsPadding()
                                )
                            }
                        }
                    ) {
                        val navController = rememberNavController()
                        NavHost(navController, startDestination = "search") {
                            composable("search") { ClaimsList(mainViewModel) }
                            composable("search/{claimId}") { ClaimsList(mainViewModel = mainViewModel) }
                            composable("guide") { FactCheckGuide() }
                        }
                    }
                }
            }
        }

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

    companion object {
        private val topbarElevation = 4.dp
    }
}
