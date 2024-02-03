package dev.fobo66.factcheckerassistant.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import dev.fobo66.factcheckerassistant.ui.guide.FactCheckGuide
import dev.fobo66.factcheckerassistant.ui.list.ClaimDetails
import dev.fobo66.factcheckerassistant.ui.list.ClaimsSearch
import dev.fobo66.factcheckerassistant.util.Screen
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

private val bottomBarItems = persistentListOf(
    Screen.Search,
    Screen.Guide
)

@Composable
fun MainActivityContent(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    var query by rememberSaveable {
        mutableStateOf("")
    }

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            BottomNavBar(
                currentRoute = currentRoute,
                bottomBarItems = bottomBarItems,
                onItemClick = {
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.DESTINATION_SEARCH,
            modifier = Modifier.padding(
                innerPadding
            )
        ) {
            composable(Screen.DESTINATION_SEARCH) {
                val claims = mainViewModel.claims.collectAsLazyPagingItems()

                ClaimsSearch(
                    query = query,
                    onQueryChange = {
                        query = it
                    },
                    onSearch = {
                        mainViewModel.search(it)
                    },
                    claims = claims,
                    onSearchResultClick = {
                        mainViewModel.selectClaim(it)
                        navController.navigate(Screen.DESTINATION_SEARCH_DETAILS)
                    }
                )
            }
            composable(Screen.DESTINATION_SEARCH_DETAILS) {
                val claim by mainViewModel.selectedClaim.collectAsStateWithLifecycle()
                ClaimDetails(claim)
            }
            composable(Screen.DESTINATION_GUIDE) {
                FactCheckGuide()
            }
        }
    }
}

@Composable
fun BottomNavBar(
    currentRoute: String?,
    bottomBarItems: ImmutableList<Screen>,
    onItemClick: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        bottomBarItems.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        screen.icon,
                        contentDescription = null
                    )
                },
                label = { Text(stringResource(screen.resourceId)) },
                selected = screen.route == currentRoute,
                onClick = {
                    onItemClick(screen)
                }
            )
        }
    }
}
