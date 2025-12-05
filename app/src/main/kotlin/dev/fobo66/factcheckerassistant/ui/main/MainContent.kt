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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import dev.fobo66.factcheckerassistant.ui.guide.FactCheckGuide
import dev.fobo66.factcheckerassistant.ui.list.ClaimDetails
import dev.fobo66.factcheckerassistant.ui.list.ClaimsSearch
import dev.fobo66.factcheckerassistant.util.ROUTE_GUIDE
import dev.fobo66.factcheckerassistant.util.ROUTE_SEARCH
import dev.fobo66.factcheckerassistant.util.ROUTE_SEARCH_DETAILS
import dev.fobo66.factcheckerassistant.util.Screen
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun MainActivityContent(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val (query, onQueryChange) = rememberSaveable {
        mutableStateOf("")
    }

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val bottomBarItems = remember {
                persistentListOf(
                    Screen.Search,
                    Screen.Guide
                )
            }
            BottomNavBar(
                currentRoute = currentRoute,
                bottomBarItems = bottomBarItems,
                onItemClick = {
                    navController.navigateToScreen(it)
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = ROUTE_SEARCH,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(ROUTE_SEARCH) {
                val claims = mainViewModel.claims.collectAsLazyPagingItems()

                ClaimsSearch(
                    query = query,
                    onQueryChange = onQueryChange,
                    onSearch = {
                        mainViewModel.search(it)
                    },
                    claims = claims,
                    onSearchResultClick = {
                        mainViewModel.selectClaim(it)
                        navController.navigate(ROUTE_SEARCH_DETAILS)
                    }
                )
            }
            composable(ROUTE_SEARCH_DETAILS) {
                val claim by mainViewModel.selectedClaim.collectAsStateWithLifecycle()
                ClaimDetails(claim)
            }
            composable(ROUTE_GUIDE) {
                FactCheckGuide()
            }
        }
    }
}

private fun NavController.navigateToScreen(screen: Screen) {
    navigate(screen.route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
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
                        painterResource(screen.icon),
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
