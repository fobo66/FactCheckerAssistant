package dev.fobo66.factcheckerassistant.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItem
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.paging.compose.collectAsLazyPagingItems
import dev.fobo66.factcheckerassistant.nav.Screen
import dev.fobo66.factcheckerassistant.nav.rememberBackStack
import dev.fobo66.factcheckerassistant.nav.rememberNavSuiteItems
import dev.fobo66.factcheckerassistant.ui.guide.FactCheckGuide
import dev.fobo66.factcheckerassistant.ui.list.ClaimDetails
import dev.fobo66.factcheckerassistant.ui.list.ClaimDetailsPlaceholder
import dev.fobo66.factcheckerassistant.ui.list.ClaimsSearch
import dev.zacsweers.metrox.viewmodel.metroViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun MainActivityContent(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = metroViewModel()
) {
    val backStack = rememberBackStack(Screen.Search)
    val navSuiteItems = rememberNavSuiteItems()

    NavigationSuiteScaffold(
        navigationItems = {
            navSuiteItems.forEach { screen ->
                NavigationSuiteItem(
                    icon = {
                        Icon(
                            painterResource(screen.icon),
                            contentDescription = null
                        )
                    },
                    label = { Text(stringResource(screen.resourceId)) },
                    selected = screen.route == backStack.lastOrNull(),
                    onClick = { backStack.add(screen.route) }
                )
            }
        },
        modifier = modifier
    ) {
        NavDisplay(
            backStack = backStack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            sceneStrategies = listOf(rememberListDetailSceneStrategy()),
            entryProvider = entryProvider {
                entry<Screen.Search>(
                    metadata = ListDetailSceneStrategy.listPane {
                        ClaimDetailsPlaceholder(modifier = Modifier.fillMaxSize())
                    }
                ) {
                    val claims = mainViewModel.claims.collectAsLazyPagingItems()

                    ClaimsSearch(
                        onSearch = { query ->
                            mainViewModel.search(query)
                        },
                        claims = claims,
                        onSearchResultClick = { claim ->
                            mainViewModel.selectClaim(claim)
                            backStack.add(
                                Screen.SearchResult
                            )
                        }
                    )
                }
                entry<Screen.SearchResult>(
                    metadata = ListDetailSceneStrategy.detailPane()
                ) {
                    val claim by mainViewModel.selectedClaim.collectAsStateWithLifecycle()
                    ClaimDetails(claim)
                }
                guideEntry()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun EntryProviderScope<Screen>.guideEntry() {
    entry<Screen.Guide>(
        metadata = ListDetailSceneStrategy.detailPane()
    ) {
        FactCheckGuide()
    }
}
