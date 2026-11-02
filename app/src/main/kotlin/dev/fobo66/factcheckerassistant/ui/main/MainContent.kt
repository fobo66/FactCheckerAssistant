package dev.fobo66.factcheckerassistant.ui.main

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItem
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import dev.zacsweers.metrox.viewmodel.metroViewModel
import kotlinx.collections.immutable.persistentListOf

@Composable
fun MainActivityContent(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = metroViewModel()
) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarItems = remember {
        persistentListOf(
            Screen.Search,
            Screen.Guide
        )
    }

    NavigationSuiteScaffold(
        navigationItems = {
            bottomBarItems.forEach { screen ->
                NavigationSuiteItem(
                    icon = {
                        Icon(
                            painterResource(screen.icon),
                            contentDescription = null
                        )
                    },
                    label = { Text(stringResource(screen.resourceId)) },
                    selected = screen.route == navBackStackEntry?.destination?.route,
                    onClick = {
                        navController.navigateToScreen(screen)
                    }
                )
            }
        },
        modifier = modifier
    ) {
        NavHost(
            navController,
            startDestination = ROUTE_SEARCH
        ) {
            composable(ROUTE_SEARCH) {
                val claims = mainViewModel.claims.collectAsLazyPagingItems()

                ClaimsSearch(
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
