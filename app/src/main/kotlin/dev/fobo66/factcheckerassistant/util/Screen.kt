package dev.fobo66.factcheckerassistant.util

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import dev.fobo66.factcheckerassistant.R
import dev.fobo66.factcheckerassistant.ui.icons.GuideIcon

@Immutable
sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
) {
    @Immutable
    data object Search : Screen(
        DESTINATION_SEARCH,
        R.string.main_fragment_title,
        Icons.Default.Search
    )

    @Immutable
    data object Guide : Screen(DESTINATION_GUIDE, R.string.guide_fragment_title, GuideIcon)

    companion object {
        const val DESTINATION_SEARCH = "search"
        const val DESTINATION_SEARCH_DETAILS = "details"
        const val DESTINATION_GUIDE = "guide"
    }
}
