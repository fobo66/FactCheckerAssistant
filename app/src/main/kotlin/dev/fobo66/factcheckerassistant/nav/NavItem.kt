package dev.fobo66.factcheckerassistant.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import dev.fobo66.factcheckerassistant.R
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Stable
sealed class NavItem(
    val route: Screen,
    @param:StringRes val resourceId: Int,
    @param:DrawableRes val icon: Int
) {
    @Immutable
    data object Search : NavItem(
        Screen.Search,
        R.string.main_fragment_title,
        R.drawable.ic_search
    )

    @Immutable
    data object Guide :
        NavItem(Screen.Guide, R.string.guide_fragment_title, R.drawable.ic_guide)
}

@Composable
fun rememberNavSuiteItems(): PersistentList<NavItem> = remember {
    persistentListOf(
        NavItem.Search,
        NavItem.Guide
    )
}
