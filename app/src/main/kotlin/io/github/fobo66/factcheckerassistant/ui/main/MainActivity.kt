package io.github.fobo66.factcheckerassistant.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import dagger.hilt.android.AndroidEntryPoint
import io.github.fobo66.factcheckerassistant.R
import io.github.fobo66.factcheckerassistant.ui.guide.FactCheckGuide
import io.github.fobo66.factcheckerassistant.ui.list.ClaimDetails
import io.github.fobo66.factcheckerassistant.ui.list.ClaimsSearch
import io.github.fobo66.factcheckerassistant.ui.theme.FactCheckerAssistantTheme
import io.github.fobo66.factcheckerassistant.util.Screen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val bottomBarItems = listOf(
        Screen.Search,
        Screen.Guide
    )

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            FactCheckerAssistantTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = {
                        MediumTopAppBar(
                            title = {
                                Text(
                                    stringResource(R.string.app_name)
                                )
                            }
                        )
                    },
                    bottomBar = {
                        BottomNavBar(navController)
                    }
                ) { innerPadding ->
                    val mainViewModel: MainViewModel = hiltViewModel()

                    NavHost(navController, startDestination = "search") {
                        composable("search") {
                            val claims = mainViewModel.claims.collectAsLazyPagingItems()
                            var query by rememberSaveable {
                                mutableStateOf("")
                            }

                            ClaimsSearch(
                                modifier = Modifier.padding(innerPadding),
                                query = query,
                                claims = claims,
                                onSearch = {
                                    query = it
                                    mainViewModel.search(it)
                                },
                                onSearchResultClick = {
                                    mainViewModel.selectClaim(it)
                                    navController.navigate("details")
                                }
                            )
                        }
                        composable("details") {
                            val claim by mainViewModel.selectedClaim.collectAsStateWithLifecycle()
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
        NavigationBar(
            modifier = modifier
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            bottomBarItems.forEach { screen ->
                NavigationBarItem(
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
