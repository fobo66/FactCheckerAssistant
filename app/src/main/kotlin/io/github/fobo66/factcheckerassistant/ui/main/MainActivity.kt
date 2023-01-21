package io.github.fobo66.factcheckerassistant.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import io.github.fobo66.factcheckerassistant.ui.icons.Search
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
                val mainViewModel: MainViewModel = hiltViewModel()
                var query by rememberSaveable {
                    mutableStateOf("")
                }
                Scaffold(
                    topBar = {
                        FactCheckTopAppBar(
                            query = query,
                            onQueryChanged = {
                                query = it
                                mainViewModel.search(it)
                            })
                    },
                    bottomBar = {
                        BottomNavBar(navController)
                    }
                ) { innerPadding ->
                    NavHost(navController, startDestination = "search") {
                        composable("search") {
                            val claims = mainViewModel.claims.collectAsLazyPagingItems()

                            ClaimsSearch(
                                claims = claims,
                                modifier = Modifier.padding(innerPadding)
                            ) {
                                mainViewModel.selectClaim(it)
                                navController.navigate("details")
                            }
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
    @OptIn(ExperimentalMaterial3Api::class)
    private fun FactCheckTopAppBar(
        query: String,
        onQueryChanged: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        TopAppBar(
            title = {
                TextField(
                    value = query,
                    onValueChange = onQueryChanged,
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = {
                        Text(stringResource(R.string.search_hint))
                    },
                    label = {
                        Text(stringResource(R.string.search_hint))
                    },
                    leadingIcon = {
                        Icon(imageVector = Search, contentDescription = null)
                    },
                    singleLine = true
                )
            },
            scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
            modifier = modifier
        )
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
