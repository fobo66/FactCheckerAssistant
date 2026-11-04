package dev.fobo66.factcheckerassistant.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import dev.fobo66.factcheckerassistant.R

@Stable
sealed class BottomNavItem(
    val route: Screen,
    @param:StringRes val resourceId: Int,
    @param:DrawableRes val icon: Int
) {
    @Immutable
    data object Search : BottomNavItem(
        Screen.Search,
        R.string.main_fragment_title,
        R.drawable.ic_search
    )

    @Immutable
    data object Guide :
        BottomNavItem(Screen.Guide, R.string.guide_fragment_title, R.drawable.ic_guide)
}

const val ROUTE_SEARCH = "search"
const val ROUTE_SEARCH_DETAILS = "details"
const val ROUTE_GUIDE = "guide"
