package dev.fobo66.factcheckerassistant.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import dev.fobo66.factcheckerassistant.R

@Stable
sealed class Screen(
    val route: String,
    @param:StringRes val resourceId: Int,
    @param:DrawableRes val icon: Int
) {
    @Immutable
    data object Search : Screen(
        ROUTE_SEARCH,
        R.string.main_fragment_title,
        R.drawable.ic_search
    )

    @Immutable
    data object Guide : Screen(ROUTE_GUIDE, R.string.guide_fragment_title, R.drawable.ic_guide)
}

const val ROUTE_SEARCH = "search"
const val ROUTE_SEARCH_DETAILS = "details"
const val ROUTE_GUIDE = "guide"
