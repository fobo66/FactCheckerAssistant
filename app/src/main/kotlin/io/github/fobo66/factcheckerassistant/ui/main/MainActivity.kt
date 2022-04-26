package io.github.fobo66.factcheckerassistant.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.github.fobo66.factcheckerassistant.R
import io.github.fobo66.factcheckerassistant.ui.guide.FactCheckGuide
import io.github.fobo66.factcheckerassistant.ui.list.ClaimDetails
import io.github.fobo66.factcheckerassistant.ui.list.ClaimsSearch
import io.github.fobo66.factcheckerassistant.ui.theme.FactCheckerAssistantTheme
import io.github.fobo66.factcheckerassistant.util.Screen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlin.time.ExperimentalTime

@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@FlowPreview
@ExperimentalTime
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val bottomBarItems = listOf(
        Screen.Search,
        Screen.Guide
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            FactCheckerAssistantTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = {
                        Surface(
                            color = MaterialTheme.colors.primarySurface,
                            elevation = 4.dp
                        ) {
                            TopAppBar(
                                title = {
                                    Text(
                                        stringResource(R.string.app_name)
                                    )
                                },
                                backgroundColor = Color.Transparent,
                                contentColor = contentColorFor(MaterialTheme.colors.primarySurface),
                                elevation = 0.dp,
                                modifier = Modifier.statusBarsPadding()
                            )
                        }
                    },
                    bottomBar = {
                        Surface(
                            color = MaterialTheme.colors.primarySurface,
                            elevation = 4.dp
                        ) {
                            BottomNavBar(navController, Modifier.navigationBarsPadding())
                        }
                    }
                ) { innerPadding ->
                    val mainViewModel: MainViewModel = hiltViewModel()

                    NavHost(navController, startDestination = "search") {
                        composable("search") {
                            ClaimsSearch(
                                modifier = Modifier.padding(innerPadding),
                                mainViewModel = mainViewModel,
                                onSearchResultClick = {
                                    mainViewModel.selectClaim(it)
                                    navController.navigate("details")
                                }
                            )
                        }
                        composable("details") {
                            val claim by mainViewModel.selectedClaim.collectAsState()
                            ClaimDetails(
                                claim,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable("guide") {
                            FactCheckGuide(
                                modifier = Modifier.padding(
                                    innerPadding
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun BottomNavBar(navController: NavHostController, modifier: Modifier = Modifier) {
        BottomNavigation(
            modifier = modifier,
            elevation = 0.dp,
            backgroundColor = Color.Transparent,
            contentColor = contentColorFor(MaterialTheme.colors.primarySurface)
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            bottomBarItems.forEach { screen ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            screen.icon,
                            contentDescription = null
                        )
                    },
                    label = { Text(stringResource(screen.resourceId)) },
                    selected = currentDestination?.hierarchy
                        ?.any { it.route == screen.route } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}
